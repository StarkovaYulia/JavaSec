package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.service.ProjectService;
import ru.tsu.hits.springdb2.service.TaskService;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectDto createProject(@RequestBody CreateUpdateProjectDto createUpdateProjectDto) {
        return projectService.createProject(createUpdateProjectDto);
    }

    @GetMapping(value = "/{id}")
    public ProjectDto getProject(@PathVariable String id) {
        return projectService.getProjectDtoById(id);
    }

}