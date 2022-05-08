package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Transactional
    public BookDto save(CreateUpdateBookDto createUpdateBookDto) {

        var author = authorService.getAuthorEntityById(createUpdateBookDto.getAuthorId());

        var entity = new BookEntity(
                UUID.randomUUID().toString(),
                createUpdateBookDto.getName(),
                author,
                createUpdateBookDto.getReleaseDate(),
                createUpdateBookDto.getGenre()
        );
        var savedEntity = bookRepository.save(entity);

        return new BookDto(
                savedEntity.getUuid(),
                savedEntity.getName(),
                authorService.getAuthorFullName(author),
                savedEntity.getReleaseDate(),
                savedEntity.getGenre()
        );
    }

    public BookDto getById(String id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return new BookDto(
                entity.getUuid(),
                entity.getName(),
                authorService.getAuthorFullName(entity.getAuthor()),
                entity.getReleaseDate(),
                entity.getGenre()
        );
    }

}
