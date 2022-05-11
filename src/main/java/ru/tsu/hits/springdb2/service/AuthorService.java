package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.dto.converter.AuthorDtoConverter;
import ru.tsu.hits.springdb2.entity.AuthorEntity;
import ru.tsu.hits.springdb2.entity.BookEntity;
import ru.tsu.hits.springdb2.exception.AuthorNotFoundException;
import ru.tsu.hits.springdb2.repository.AuthorRepository;
import ru.tsu.hits.springdb2.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;

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
    public AuthorDto getAuthorDtoById(String uuid) {
        AuthorEntity authorEntity = getAuthorEntityById(uuid);

        return AuthorDtoConverter.convertEntityToDto(authorEntity, getBooksByAuthor(authorEntity));
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
