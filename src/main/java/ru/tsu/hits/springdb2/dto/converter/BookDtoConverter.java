package ru.tsu.hits.springdb2.dto.converter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.service.AuthorService;

@Service
@AllArgsConstructor
public class BookDtoConverter {
    public static BookEntity convertDtoToEntity(String id, CreateUpdateBookDto dto, AuthorService authorService) {
        BookEntity entity = new BookEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto, authorService);

        return entity;
    }

    public static void updateEntityFromDto(BookEntity entity, CreateUpdateBookDto dto, AuthorService authorService) {
        entity.setName(dto.getName());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setGenre(dto.getGenre());

        var author = authorService.getAuthorEntityById(dto.getAuthorId());
        entity.setAuthor(author);
    }

    public static BookDto convertEntityToDto(BookEntity entity) {
        BookDto dto = new BookDto();

        dto.setId(entity.getUuid());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor().getFullName());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setGenre(entity.getGenre());

        return dto;
    }
}
