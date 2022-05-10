package ru.tsu.hits.springdb2.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.service.PasswordService;

import java.util.List;


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
}
