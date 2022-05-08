package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectDtoConverter {

    public static ProjectEntity convertDtoToEntity(CreateUpdateProjectDto dto) {
        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setUuid(UUID.randomUUID().toString());
        projectEntity.setCreationDate(dto.getCreationDate());
        projectEntity.setEditDate(dto.getEditDate());
        projectEntity.setName(dto.getName());
        projectEntity.setDescription(dto.getDescription());

        return projectEntity;

    }

    public static ProjectDto convertEntityToDto(ProjectEntity projectEntity, List<TaskEntity> tasksEntity) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(projectEntity.getUuid());
        projectDto.setCreationDate(projectEntity.getCreationDate());
        projectDto.setEditDate(projectEntity.getEditDate());
        projectDto.setName(projectEntity.getName());
        projectDto.setDescription(projectEntity.getDescription());
        //projectDto.setTasks(convertTasksToDto(tasksEntity));

        return projectDto;
    }

    private static List<TaskDto> convertTasksToDto(List<TaskEntity> tasksEntities) {
        List<TaskDto> result = new ArrayList<>();

        tasksEntities.forEach(element -> {
            TaskDto taskDto = new TaskDto();

            taskDto.setId(element.getUuid());
            taskDto.setCreationDate(element.getCreationDate());
            taskDto.setEditDate(element.getEditDate());
            taskDto.setHeader(element.getHeader());
            taskDto.setDescription(element.getDescription());
            taskDto.setPriority(element.getPriority());
            taskDto.setTemporaryMark(element.getTemporaryMark());
            taskDto.setUserCreator(element.getUserCreator().getUuid());
            taskDto.setUserExecutor(element.getUserExecutor().getUuid());
            taskDto.setProject(element.getProject().getUuid());
            taskDto.setComments(convertCommentsToDto(element.getComments()));

            result.add(taskDto);
        });

        return result;
    }

    public static List<CommentDto> convertCommentsToDto(List<CommentEntity> commentEntities) {

        List<CommentDto> result = new ArrayList<>();

        commentEntities.forEach(element -> {
            CommentDto commentDto = new CommentDto();

            commentDto.setId(element.getUuid());
            commentDto.setCreationDate(element.getCreationDate());
            commentDto.setEditDate(element.getEditDate());
            commentDto.setUser(element.getUser().getUuid());

            result.add(commentDto);
        });

        return result;

    }

}
