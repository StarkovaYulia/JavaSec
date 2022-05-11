package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.dto.converter.ProjectDtoConverter;
import ru.tsu.hits.springdb2.dto.converter.TaskDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.exception.ProjectNotFoundException;
import ru.tsu.hits.springdb2.exception.TaskNotFoundException;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public ProjectDto createProject(CreateUpdateProjectDto dto) {

        ProjectEntity projectEntity = ProjectDtoConverter.convertDtoToEntity(dto);

        projectEntity = projectRepository.save(projectEntity);

        return ProjectDtoConverter.convertEntityToDto(projectEntity, getTasksByProject(projectEntity));

    }

    private List<TaskEntity> getTasksByProject(ProjectEntity projectEntity) {
        return taskRepository.findByProject(projectEntity);
    }

    @Transactional
    public ProjectEntity getProjectEntityById(String uuid) {
        return projectRepository.findById(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("There is no such project"));
    }

    @Transactional
    public ProjectDto getProjectDtoById(String uuid) {
        ProjectEntity projectEntity = getProjectEntityById(uuid);

        return ProjectDtoConverter.convertEntityToDto(projectEntity, getTasksByProject(projectEntity));
    }




}
