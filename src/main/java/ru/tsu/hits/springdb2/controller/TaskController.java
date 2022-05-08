package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.service.CommentService;
import ru.tsu.hits.springdb2.service.TaskService;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto createTask(@RequestBody CreateUpdateTaskDto createUpdateTaskDto) {
        return taskService.createTask(createUpdateTaskDto);
    }

    @GetMapping(value = "/{id}")
    public TaskDto getTask(@PathVariable String id) {
        return taskService.getTaskDtoById(id);
    }

}