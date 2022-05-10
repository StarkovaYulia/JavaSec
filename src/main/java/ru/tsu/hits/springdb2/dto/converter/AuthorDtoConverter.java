package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.entity.AuthorEntity;

import java.util.stream.Collectors;

public class AuthorDtoConverter {
    public static AuthorEntity convertDtoToEntity(String id, CreateUpdateAuthorDto dto) {
        var entity = new AuthorEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto);

        return entity;
    }

    public static void updateEntityFromDto(AuthorEntity entity, CreateUpdateAuthorDto dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
    }

    public static AuthorDto convertEntityToDto(AuthorEntity entity) {
        var dto = new AuthorDto();

        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setId(entity.getUuid());

        var books = entity.getBooks()
                .stream()
                .map(BookDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
        dto.setBooks(books);

        return dto;
    }

}
