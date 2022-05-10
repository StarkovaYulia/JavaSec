package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.dto.converter.TaskDtoConverter;
import ru.tsu.hits.springdb2.entity.*;
import ru.tsu.hits.springdb2.exception.ProjectNotFoundException;
import ru.tsu.hits.springdb2.exception.TaskNotFoundException;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskDtoConverter taskDtoConverter;

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

    private TaskEntity getTaskEntityById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }
}
