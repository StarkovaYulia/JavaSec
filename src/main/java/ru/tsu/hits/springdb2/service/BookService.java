package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.dto.converter.BookDtoConverter;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Transactional
    public BookDto save(CreateUpdateBookDto dto) {
        var author = authorService.getAuthorEntityById(dto.getAuthorId());

        var entity = BookDtoConverter.convertDtoToEntity(dto, author);

        entity = bookRepository.save(entity);

        return BookDtoConverter.convertEntityToDto(entity);
    }

    @Transactional
    public BookDto update(String id, CreateUpdateBookDto dto) {
        var entityOptional = bookRepository.findById(id);

        var author = authorService.getAuthorEntityById(dto.getAuthorId());

        BookEntity entity;
        if (entityOptional.isEmpty()) {
            entity = BookDtoConverter.convertDtoToEntity(dto, author);
        } else {
            entity = entityOptional.get();
            BookDtoConverter.updateEntityFromDto(entity, dto, author);
        }

        entity = bookRepository.save(entity);

        return BookDtoConverter.convertEntityToDto(entity);
    }

    public void delete(String id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        bookRepository.delete(entity);
    }

    @Transactional(readOnly = false)
    public BookDto getById(String id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return BookDtoConverter.convertEntityToDto(entity);
    }

    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
