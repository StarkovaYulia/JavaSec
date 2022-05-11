package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.dto.converter.CommentDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.exception.CommentNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentDtoConverter commentDtoConverter;

    @Transactional
    public CommentDto createOrUpdate(CreateUpdateCommentDto dto, String id) {
        if (id == null) id = "";

        var entityOptional = commentRepository.findById(id);

        CommentEntity entity;
        if (entityOptional.isEmpty()) {
            entity = commentDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);
        } else {
            entity = entityOptional.get();
            commentDtoConverter.updateEntityFromDto(entity, dto);
        }

        entity = commentRepository.save(entity);

        return commentDtoConverter.convertEntityToDto(entity);
    }

    @Transactional
    public void delete(String id) {
        var entity = getCommentEntityById(id);
        commentRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDto getById(String uuid) {
        var entity = getCommentEntityById(uuid);
        return commentDtoConverter.convertEntityToDto(entity);
    }

    private CommentEntity getCommentEntityById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id " + id + " not found"));
    }
}
