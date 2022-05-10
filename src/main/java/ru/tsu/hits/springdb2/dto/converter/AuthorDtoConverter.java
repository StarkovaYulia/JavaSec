package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.entity.AuthorEntity;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.service.AuthorService;
import ru.tsu.hits.springdb2.service.ProjectService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthorDtoConverter {

    public static AuthorEntity convertDtoToEntity(String id, CreateUpdateAuthorDto dto) {
        AuthorEntity authorEntity = new AuthorEntity();

        authorEntity.setUuid(id);
        updateEntityFromDto(authorEntity, dto);

        return authorEntity;
    }

    public static void updateEntityFromDto(AuthorEntity entity, CreateUpdateAuthorDto dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
    }

    public static AuthorDto convertEntityToDto(AuthorEntity authorEntity, List<BookEntity> bookEntities) {
        AuthorDto authorDto = new AuthorDto();

        authorDto.setLastName(authorEntity.getLastName());
        authorDto.setFirstName(authorEntity.getFirstName());
        authorDto.setId(authorEntity.getUuid());
        authorDto.setBooks(convertBooksToDto(bookEntities));

        return authorDto;
    }

    private static List<BookDto> convertBooksToDto(List<BookEntity> bookEntities) {
        List<BookDto> result = new ArrayList<>();

        bookEntities.forEach(element -> {
            BookDto bookDto = new BookDto();

            bookDto.setAuthor(element.getAuthor().getFirstName() + " " + element.getAuthor().getLastName());
            bookDto.setGenre(element.getGenre());
            bookDto.setName(element.getName());
            bookDto.setReleaseDate(element.getReleaseDate());
            bookDto.setId(element.getUuid());

            result.add(bookDto);
        });

        return result;
    }

}
