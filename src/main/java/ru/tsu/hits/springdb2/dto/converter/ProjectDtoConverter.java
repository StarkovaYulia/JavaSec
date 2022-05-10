package ru.tsu.hits.springdb2.dto.converter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.entity.ProjectEntity;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectDtoConverter {
    private final TaskDtoConverter taskDtoConverter;

    public ProjectEntity convertDtoToEntity(String id, CreateUpdateProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto);

        return entity;
    }

    public void updateEntityFromDto(ProjectEntity entity, CreateUpdateProjectDto dto) {
        entity.setCreationDate(dto.getCreationDate());
        entity.setEditDate(dto.getEditDate());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

    public ProjectDto convertEntityToDto(ProjectEntity entity) {
        var dto = new ProjectDto();

        dto.setId(entity.getUuid());
        dto.setCreationDate(entity.getCreationDate());
        dto.setEditDate(entity.getEditDate());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        var tasks = entity.getTasks()
                .stream()
                .map(taskDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
        dto.setTasks(tasks);

        return dto;
    }
}
