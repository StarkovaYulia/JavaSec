package ru.tsu.hits.springdb2.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.UsersRepository;


@Service
@RequiredArgsConstructor
public class TaskDtoConverter {
    private final UsersRepository usersRepository;

    private final ProjectRepository projectRepository;

    public TaskEntity convertDtoToEntity(String id, CreateUpdateTaskDto dto) {
        TaskEntity entity = new TaskEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto);

        return entity;
    }

    public void updateEntityFromDto(TaskEntity entity, CreateUpdateTaskDto dto) {
        entity.setCreationDate(dto.getCreationDate());
        entity.setEditDate(dto.getEditDate());
        entity.setHeader(dto.getHeader());
        entity.setDescription(dto.getDescription());
        entity.setPriority(dto.getPriority());
        entity.setTemporaryMark(dto.getTemporaryMark());

        entity.setUserCreator(getUserById(dto.getUserCreator()));
        entity.setUserExecutor(getUserById(dto.getUserExecutor()));
        entity.setProject(getProjectById(dto.getProject()));
    }

    public TaskDto convertEntityToDto(TaskEntity taskEntity) {

        TaskDto dto = new TaskDto();

        dto.setId(taskEntity.getUuid());
        dto.setCreationDate(taskEntity.getCreationDate());
        dto.setEditDate(taskEntity.getEditDate());
        dto.setHeader(taskEntity.getHeader());
        dto.setDescription(taskEntity.getDescription());
        dto.setPriority(taskEntity.getPriority());
        dto.setTemporaryMark(taskEntity.getTemporaryMark());
        dto.setUserCreator(taskEntity.getUserCreator().getUuid());
        dto.setUserExecutor(taskEntity.getUserExecutor().getUuid());
        dto.setProject(taskEntity.getProject().getUuid());

        return dto;
    }

    private UsersEntity getUserById(String id) {
        return usersRepository.findById(id).orElseThrow();
    }

    private ProjectEntity getProjectById(String id) {
        return projectRepository.findById(id).orElseThrow();
    }
}
