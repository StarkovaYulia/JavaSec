package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.dto.converter.BookDtoConverter;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.exception.BookNotFoundException;
import ru.tsu.hits.springdb2.repository.BookRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Transactional
    public BookDto createOrUpdate(CreateUpdateBookDto dto, String id) {
        if (id == null) id = "";

        var entityOptional = bookRepository.findById(id);

        var author = authorService.getAuthorEntityById(dto.getAuthorId());

        BookEntity entity;
        if (entityOptional.isEmpty()) {
            entity = BookDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto, author);
        } else {
            entity = entityOptional.get();
            BookDtoConverter.updateEntityFromDto(entity, dto, author);
        }

        entity = bookRepository.save(entity);

        return BookDtoConverter.convertEntityToDto(entity);
    }

    @Transactional
    public void delete(String id) {
        var entity = getBookEntityById(id);
        bookRepository.delete(entity);
    }

    @Transactional(readOnly = false)
    public BookDto getById(String id) {
        var entity = getBookEntityById(id);
        return BookDtoConverter.convertEntityToDto(entity);
    }

    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private BookEntity getBookEntityById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }
}
