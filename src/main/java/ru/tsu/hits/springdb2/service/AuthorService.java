package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.dto.converter.AuthorDtoConverter;
import ru.tsu.hits.springdb2.entity.AuthorEntity;
import ru.tsu.hits.springdb2.exception.AuthorNotFoundException;
import ru.tsu.hits.springdb2.repository.AuthorRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorDto createOrUpdate(CreateUpdateAuthorDto dto, String id) {
        if (id == null) id = "";

        var authorEntityOptional = authorRepository.findById(id);
        AuthorEntity authorEntity;
        if (authorEntityOptional.isEmpty()) {
            authorEntity = AuthorDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);
        } else {
            authorEntity = authorEntityOptional.get();
            AuthorDtoConverter.updateEntityFromDto(authorEntity, dto);
        }

        authorEntity = authorRepository.save(authorEntity);

        return AuthorDtoConverter.convertEntityToDto(authorEntity);
    }

    @Transactional
    public void deleteAuthor(String id) {
        var authorEntity = getAuthorEntityById(id);

        authorRepository.delete(authorEntity);
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthor(String uuid) {
        var entity = getAuthorEntityById(uuid);
        return AuthorDtoConverter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorEntity getAuthorEntityById(String uuid) {
        return authorRepository.findById(uuid)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + uuid + " not found"));
    }
}
