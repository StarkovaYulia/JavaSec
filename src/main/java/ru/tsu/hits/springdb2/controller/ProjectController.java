package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = "/")
    public List<ProjectDto> getAll() {
        return projectService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ProjectDto getById(@PathVariable String id) {
        return projectService.getById(id);
    }

    @PostMapping
    public ProjectDto create(@RequestBody CreateUpdateProjectDto dto) {
        return projectService.createOrUpdate(dto, null);
    }

    @PutMapping(value = "/{id}")
    public ProjectDto update(@PathVariable String id, @RequestBody CreateUpdateProjectDto dto) {
        return projectService.createOrUpdate(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        projectService.delete(id);
    }
}