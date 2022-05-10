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

    @PostMapping
    public TaskDto create(@RequestBody CreateUpdateTaskDto dto) {
        return taskService.createOrUpdate(dto, null);
    }

    @PutMapping("/{id}")
    public TaskDto create(@PathVariable String id, @RequestBody CreateUpdateTaskDto dto) {
        return taskService.createOrUpdate(dto, id);
    }

    @DeleteMapping("/{id}")
    public void create(@PathVariable String id) {
        taskService.delete(id);
    }
}