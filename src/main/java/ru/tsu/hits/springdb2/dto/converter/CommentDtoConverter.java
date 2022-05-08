package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.UUID;

public class CommentDtoConverter {

    public static CommentEntity convertDtoToEntity(CreateUpdateCommentDto dto) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setUuid(UUID.randomUUID().toString());
        commentEntity.setCreationDate(dto.getCreationDate());
        commentEntity.setEditDate(dto.getEditDate());

        return commentEntity;
    }

    public static CommentDto convertEntityToDto(CommentEntity commentEntity, UsersEntity usersEntity) {

        CommentDto commentDto = new CommentDto();

        commentDto.setId(commentEntity.getUuid());
        commentDto.setCreationDate(commentEntity.getCreationDate());
        commentDto.setEditDate(commentEntity.getEditDate());
        commentDto.setUser(commentEntity.getUser().getUuid());

        return commentDto;

    }

}
