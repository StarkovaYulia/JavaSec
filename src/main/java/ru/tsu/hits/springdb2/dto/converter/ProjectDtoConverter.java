package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.entity.ProjectEntity;

public class ProjectDtoConverter {

    public static ProjectEntity convertDtoToEntity(String id, CreateUpdateProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto);

        return entity;

    }

    public static void updateEntityFromDto(ProjectEntity entity, CreateUpdateProjectDto dto) {
        entity.setCreationDate(dto.getCreationDate());
        entity.setEditDate(dto.getEditDate());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

    public static ProjectDto convertEntityToDto(ProjectEntity projectEntity) {
        var dto = new ProjectDto();

        dto.setId(projectEntity.getUuid());
        dto.setCreationDate(projectEntity.getCreationDate());
        dto.setEditDate(projectEntity.getEditDate());
        dto.setName(projectEntity.getName());
        dto.setDescription(projectEntity.getDescription());

        return dto;
    }
}
