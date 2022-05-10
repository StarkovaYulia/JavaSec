package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    public List<TaskDto> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable String id) {
        return taskService.getById(id);
    }

    @GetMapping("/by-executor/{id}")
    public List<TaskDto> getByUserExecutorId(@PathVariable String id) {
        return taskService.getByUserExecutorId(id);
    }

    @GetMapping("/by-project/{id}")
    public List<TaskDto> getByProjectId(@PathVariable String id) {
        return taskService.getByProjectId(id);
    }

    @PostMapping
    public TaskDto create(@RequestBody CreateUpdateTaskDto dto) {
        return taskService.createOrUpdate(dto, null);
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable String id, @RequestBody CreateUpdateTaskDto dto) {
        return taskService.createOrUpdate(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        taskService.delete(id);
    }
}