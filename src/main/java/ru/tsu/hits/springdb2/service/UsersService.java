package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.TaskCsv;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.dto.converter.UserDtoConverter;
import ru.tsu.hits.springdb2.entity.*;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import java.io.InputStreamReader;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordService passwordService;
    private final UsersRepository usersRepository;
    private final UserDtoConverter userDtoConverter;

    @Value("${csvPath}")
    private String csvPath;

    public UsersEntity createEntity(TaskCsv user) throws ParseException {
        throw new RuntimeException();
        /*byte[] password = passwordService.hashPassword(String.valueOf(user.getPassword()));
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

        return usersRepository.save(entity);*/
    }

    public UsersEntity getUserEntityById(String uuid) {
        return usersRepository.findById(uuid)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public UsersDto createOrUpdate(CreateUpdateUserDto dto) {
        var entity = userDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);

        entity = usersRepository.save(entity);

        return userDtoConverter.convertEntityToDto(entity);
    }

    @Transactional
    public List<UsersDto> saveFromCsv() {
        var csvStream = UsersService.class.getResourceAsStream(csvPath);
        var tasks = new CsvToBeanBuilder<TaskCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(TaskCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        return tasks.stream()
                .map(task -> {
                    try {
                        return createEntity(task);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(userDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsersDto> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(userDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsersDto getById(String id) {
        var entity = getUserEntityById(id);
        return userDtoConverter.convertEntityToDto(entity)
    }
}