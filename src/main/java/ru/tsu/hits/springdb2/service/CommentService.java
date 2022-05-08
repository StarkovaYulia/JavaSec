package ru.tsu.hits.springdb2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.dto.converter.CommentDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;
import ru.tsu.hits.springdb2.exception.CommentNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public CommentDto createComment(CreateUpdateCommentDto dto) {

        CommentEntity commentEntity = CommentDtoConverter.convertDtoToEntity(dto);

        commentEntity.setUser(getUserById(dto.getUser()));

        var tasks = getTasksByComment(commentEntity);

        commentEntity.setTasks(tasks);

        commentEntity = commentRepository.save(commentEntity);

        return CommentDtoConverter.convertEntityToDto(commentEntity, commentEntity.getUser());

    }

    private List<TaskEntity> getTasksByComment(CommentEntity commentEntity) {
        return taskRepository.findByComments(commentEntity);
    }

    @Transactional
    public UsersEntity getUserById(String uuid) {
        return usersRepository.findById(uuid)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public CommentDto getCommentDtoById(String uuid) {
        CommentEntity commentEntity = getCommentEntityById(uuid);

        return CommentDtoConverter.convertEntityToDto(commentEntity, commentEntity.getUser());
    }

    @Transactional
    public CommentEntity getCommentEntityById(String uuid) {
        return commentRepository.findById(uuid)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id " + uuid + " not found"));
    }


}
