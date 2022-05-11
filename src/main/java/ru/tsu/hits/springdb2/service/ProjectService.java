package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.converter.ProjectDtoConverter;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.exception.ProjectNotFoundException;
import ru.tsu.hits.springdb2.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectDtoConverter projectDtoConverter;

    @Transactional
    public ProjectDto createOrUpdate(CreateUpdateProjectDto dto, String id) {
        if (id == null) id = "";

        var entityOptional = projectRepository.findById(id);

        ProjectEntity entity;
        if (entityOptional.isEmpty()) {
            entity = projectDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);
        } else {
            entity = entityOptional.get();
            projectDtoConverter.updateEntityFromDto(entity, dto);
        }

        entity = projectRepository.save(entity);

        return projectDtoConverter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public ProjectEntity getProjectEntityById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("There is no such project"));
    }

    @Transactional(readOnly = true)
    public ProjectDto getById(String uuid) {
        ProjectEntity projectEntity = getProjectEntityById(uuid);

        return projectDtoConverter.convertEntityToDto(projectEntity);
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(projectDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(String id) {
        var entity = getProjectEntityById(id);
        projectRepository.delete(entity);
    }
}
