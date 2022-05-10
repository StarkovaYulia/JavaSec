package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.TaskCsv;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.entity.*;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.transaction.Transactional;

import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;

    @Value("${csvPath}")
    private String csvPath;

    public byte[] HashPassword(String password){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,65536, 128);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = null;
        try {
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public UsersEntity CreateEntity(TaskCsv user) throws ParseException {
        byte[] password = HashPassword(String.valueOf(user.getPassword()));
        Role role = null;
        String currentRole = user.getField("role");

        switch (currentRole)
        {
            case "ADMIN":
                role = Role.ADMIN;
                break;
            case "USER":
                role = Role.USER;
                break;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date creationDate = formatter.parse(user.getField("creationDate"));
        Date editDate = formatter.parse(user.getField("editDate"));


        var entity = new UsersEntity();
        entity.setUuid(UUID.randomUUID().toString());
        entity.setFio( user.getField("fio"));
        entity.setEmail(user.getField("email"));
        entity.setPassword(password);
        entity.setRole(role);
        entity.setCreationDate(creationDate);
        entity.setEditDate(editDate);

        entity.setCreatedTasks(getCreatedTasksByUser(entity));
        entity.setExecutionTasks(getCreatedTasksByUser(entity));
        entity.setComments(getCommentsByUser(entity));

        var savedEntity = usersRepository.save(entity);
        return savedEntity;

    }

    public UsersEntity getUserEntityById(String uuid) {
        return usersRepository.findById(uuid)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public List<TaskEntity> getCreatedTasksByUser(UsersEntity user) {
        return taskRepository.findByUserCreator(user);
    }

    @Transactional
    public List<CommentEntity> getCommentsByUser(UsersEntity user) {
        return commentRepository.findByUser(user);
    }



    @Transactional
    public UsersDto save(CreateUpdateUserDto createUpdateUserDto) {
        byte[] password = HashPassword(createUpdateUserDto.getPassword());

        var entity = new UsersEntity();
        entity.setUuid(UUID.randomUUID().toString());
        entity.setFio(createUpdateUserDto.getFio());
        entity.setEmail(createUpdateUserDto.getEmail());
        entity.setPassword(password);
        entity.setRole(createUpdateUserDto.getRole());
        entity.setCreationDate(createUpdateUserDto.getCreationDate());
        entity.setEditDate(createUpdateUserDto.getEditDate());

        entity.setCreatedTasks(getCreatedTasksByUser(entity));
        entity.setExecutionTasks(getCreatedTasksByUser(entity));
        entity.setComments(getCommentsByUser(entity));

        var savedEntity = usersRepository.save(entity);

        return new UsersDto(
                savedEntity.getUuid(),
                savedEntity.getFio(),
                savedEntity.getEmail(),
                savedEntity.getPassword(),
                savedEntity.getRole(),
                savedEntity.getCreationDate(),
                savedEntity.getEditDate()
        );
    }

    @Transactional
    public void saveFromCsv() {
        saveFromCsv(csvPath);
    }

    private void saveFromCsv(String path) {
        var csvStream = UsersService.class.getResourceAsStream(path);
        var tasks = new CsvToBeanBuilder<TaskCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(TaskCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        tasks.stream()
                .forEach(i -> {
                    try {
                        CreateEntity(i);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
    }

    public UsersDto getById(String id)
    {
        var entity = usersRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return new UsersDto(
                entity.getUuid(),
                entity.getFio(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreationDate(),
                entity.getEditDate()
        );
    }
}