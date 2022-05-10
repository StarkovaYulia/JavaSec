package ru.tsu.hits.springdb2.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.TaskCsv;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.Role;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.service.PasswordService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class UserDtoConverter {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    private final PasswordService passwordService;

    public UsersEntity convertDtoToEntity(String id, CreateUpdateUserDto dto) {
        UsersEntity entity = new UsersEntity();

        var password = passwordService.hashPassword(dto.getPassword());

        entity.setUuid(id);
        updateEntityFromDto(entity, dto, password);

        return entity;
    }

    public void updateEntityFromDto(UsersEntity entity, CreateUpdateUserDto dto, byte[] password) {
        entity.setFio(dto.getFio());
        entity.setEmail(dto.getEmail());
        entity.setPassword(password);
        entity.setRole(dto.getRole());
        entity.setCreationDate(dto.getCreationDate());
        entity.setEditDate(dto.getEditDate());

        var tasks = getCreatedTasksByUser(entity);
        entity.setCreatedTasks(tasks);
        entity.setExecutionTasks(tasks);

        entity.setComments(getCommentsByUser(entity));
    }

    public UsersDto convertEntityToDto(UsersEntity entity) {
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

    private List<TaskEntity> getCreatedTasksByUser(UsersEntity user) {
        return taskRepository.findByUserCreator(user);
    }

    private List<CommentEntity> getCommentsByUser(UsersEntity user) {
        return commentRepository.findByUser(user);
    }

    public CreateUpdateUserDto convertCsvToDto(TaskCsv user) throws ParseException {
        Role role = Role.valueOf(user.getRole());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date creationDate = formatter.parse(user.getCreationDate());
        Date editDate = formatter.parse(user.getEditDate());

        var dto = new CreateUpdateUserDto();
        dto.setFio( user.getFio());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(role);
        dto.setCreationDate(creationDate);
        dto.setEditDate(editDate);

        return dto;
    }
}
