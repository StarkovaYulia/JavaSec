package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.dto.converter.TaskDtoConverter;
import ru.tsu.hits.springdb2.entity.*;
import ru.tsu.hits.springdb2.exception.ProjectNotFoundException;
import ru.tsu.hits.springdb2.exception.TaskNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    private final ProjectService projectService;
    private final UsersService usersService;


    @Transactional
    public TaskDto createTask(CreateUpdateTaskDto dto) {

        TaskEntity taskEntity = TaskDtoConverter.convertDtoToEntity(dto);

        taskEntity.setUserCreator(getUserById(dto.getUserCreator()));
        taskEntity.setUserExecutor(getUserById(dto.getUserExecutor()));
        taskEntity.setProject(getProjectEntityById(dto.getProject()));

        var comments = getCommentsByTask(taskEntity);
        taskEntity.setComments(comments);

        taskEntity = taskRepository.save(taskEntity);

        return TaskDtoConverter.convertEntityToDto(taskEntity, comments);
    }

    private List<CommentEntity> getCommentsByTask(TaskEntity taskEntity) {
        return commentRepository.findByTasks(taskEntity);
    }

    @Transactional
    public TaskDto getTaskDtoById(String uuid) {
        TaskEntity taskEntity = getTaskEntityById(uuid);

        return TaskDtoConverter.convertEntityToDto(taskEntity, getCommentsByTask(taskEntity));
    }

    @Transactional
    public TaskEntity getTaskEntityById(String uuid) {
        return taskRepository.findById(uuid)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + uuid + " not found"));
    }

    @Transactional
    public UsersEntity getUserById(String uuid) {
        return usersRepository.findById(uuid)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ProjectEntity getProjectEntityById(String uuid) {
        return projectRepository.findById(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("There is no such project"));
    }



}

