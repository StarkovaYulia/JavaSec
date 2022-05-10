package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.entity.AuthorEntity;
import ru.tsu.hits.springdb2.entity.BookEntity;

public class BookDtoConverter {
    public static BookEntity convertDtoToEntity(String id, CreateUpdateBookDto dto, AuthorEntity author) {
        BookEntity entity = new BookEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto, author);

        return entity;
    }

    public static void updateEntityFromDto(BookEntity entity, CreateUpdateBookDto dto, AuthorEntity author) {
        entity.setName(dto.getName());
        entity.setAuthor(author);
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setGenre(dto.getGenre());
    }

    public static BookDto convertEntityToDto(BookEntity entity) {
        BookDto dto = new BookDto();

        dto.setId(entity.getUuid());
        dto.setName(entity.getName());
        dto.setName(entity.getAuthor().getFullName());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setGenre(entity.getGenre());

        return dto;
    }
}
