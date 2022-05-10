package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.dto.converter.AuthorDtoConverter;
import ru.tsu.hits.springdb2.entity.AuthorEntity;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.exception.AuthorNotFoundException;
import ru.tsu.hits.springdb2.repository.AuthorRepository;
import ru.tsu.hits.springdb2.repository.BookRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Transactional
    public AuthorDto createAuthor(CreateUpdateAuthorDto dto) {
        AuthorEntity authorEntity = AuthorDtoConverter.convertDtoToEntity(dto);

        authorEntity = authorRepository.save(authorEntity);

        return AuthorDtoConverter.convertEntityToDto(authorEntity, getBooksByAuthor(authorEntity));
    }

    @Transactional
    public AuthorDto updateAuthor(String id, CreateUpdateAuthorDto dto) {
        var authorEntityOptional = authorRepository.findById(id);
        AuthorEntity authorEntity;
        if (authorEntityOptional.isEmpty()) {
            authorEntity = AuthorDtoConverter.convertDtoToEntity(dto);
        } else {
            authorEntity = authorEntityOptional.get();
            AuthorDtoConverter.updateEntityFromDto(authorEntity, dto);
        }

        authorEntity = authorRepository.save(authorEntity);

        return AuthorDtoConverter.convertEntityToDto(authorEntity, getBooksByAuthor(authorEntity));
    }

    @Transactional
    public void deleteAuthor(String id) {
        var authorEntity = getAuthorEntityById(id);

        authorRepository.delete(authorEntity);
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthor(String uuid) {
        AuthorEntity authorEntity = getAuthorEntityById(uuid);

        return AuthorDtoConverter.convertEntityToDto(authorEntity, getBooksByAuthor(authorEntity));
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(author -> AuthorDtoConverter.convertEntityToDto(author, getBooksByAuthor(author)))
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorEntity getAuthorEntityById(String uuid) {
        return authorRepository.findById(uuid)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + uuid + " not found"));
    }

    public List<BookEntity> getBooksByAuthor(AuthorEntity authorEntity) {
        return bookRepository.findByAuthor(authorEntity);
    }

    public String getAuthorFullName(AuthorEntity authorEntity) {
        return authorEntity.getFirstName() + " " + authorEntity.getLastName();
    }
}
