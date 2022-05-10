package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.dto.converter.TaskDtoConverter;
import ru.tsu.hits.springdb2.entity.*;
import ru.tsu.hits.springdb2.exception.TaskNotFoundException;
import ru.tsu.hits.springdb2.repository.TaskRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDtoConverter taskDtoConverter;
    private final UsersService usersService;
    private final ProjectService projectService;

    @Transactional
    public TaskDto createOrUpdate(CreateUpdateTaskDto dto, String id) {
        if (id == null) id = "";

        var entityOptional = taskRepository.findById(id);

        TaskEntity entity;
        if (entityOptional.isEmpty()) {
            entity = taskDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);
        } else {
            entity = entityOptional.get();
            taskDtoConverter.updateEntityFromDto(entity, dto);
        }

        entity = taskRepository.save(entity);

        return taskDtoConverter.convertEntityToDto(entity);
    }

    @Transactional
    public void delete(String id) {
        var entity = getTaskEntityById(id);
        taskRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskDto getById(String id) {
        var entity = getTaskEntityById(id);
        return taskDtoConverter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getByUserExecutorId(String id) {
        var user = usersService.getUserEntityById(id);

        return taskRepository.findByUserExecutor(user)
                .stream()
                .map(taskDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getByProjectId(String id) {
        var project = projectService.getProjectEntityById(id);

        return taskRepository.findByProject(project)
                .stream()
                .map(taskDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private TaskEntity getTaskEntityById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }
}
