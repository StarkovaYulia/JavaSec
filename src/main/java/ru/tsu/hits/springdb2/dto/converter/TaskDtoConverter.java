package ru.tsu.hits.springdb2.dto.converter;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDtoConverter {

    public static TaskEntity convertDtoToEntity(CreateUpdateTaskDto dto) {
        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setUuid(UUID.randomUUID().toString());
        taskEntity.setCreationDate(dto.getCreationDate());
        taskEntity.setEditDate(dto.getEditDate());
        taskEntity.setHeader(dto.getHeader());
        taskEntity.setDescription(dto.getDescription());
        taskEntity.setPriority(dto.getPriority());
        taskEntity.setTemporaryMark(dto.getTemporaryMark());

        return taskEntity;
    }

    public static TaskDto convertEntityToDto(TaskEntity taskEntity, List<CommentEntity> comments) {

        TaskDto taskDto = new TaskDto();

        taskDto.setId(taskEntity.getUuid());
        taskDto.setCreationDate(taskEntity.getCreationDate());
        taskDto.setEditDate(taskEntity.getEditDate());
        taskDto.setHeader(taskEntity.getHeader());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setPriority(taskEntity.getPriority());
        taskDto.setTemporaryMark(taskEntity.getTemporaryMark());
        taskDto.setUserCreator(taskEntity.getUserCreator().getUuid());
        taskDto.setUserExecutor(taskEntity.getUserExecutor().getUuid());
        taskDto.setProject(taskEntity.getProject().getUuid());
        taskDto.setComments(convertCommentsToDto(comments));

        return taskDto;
    }

    private static List<CommentDto> convertCommentsToDto(List<CommentEntity> commentEntities) {
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
