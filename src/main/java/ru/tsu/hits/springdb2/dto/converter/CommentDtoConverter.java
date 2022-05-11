package ru.tsu.hits.springdb2.dto.converter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.service.UsersService;

@Service
@AllArgsConstructor
public class CommentDtoConverter {
    private final UsersService usersService;

    public CommentEntity convertDtoToEntity(String id, CreateUpdateCommentDto dto) {
        var entity = new CommentEntity();

        entity.setUuid(id);
        updateEntityFromDto(entity, dto);

        return entity;
    }

    public void updateEntityFromDto(CommentEntity entity, CreateUpdateCommentDto dto) {
        entity.setCreationDate(dto.getCreationDate());
        entity.setEditDate(dto.getEditDate());
        entity.setText(dto.getText());

        var user = usersService.getUserEntityById(dto.getUser());
        entity.setUser(user);
    }

    public CommentDto convertEntityToDto(CommentEntity entity) {

        var dto = new CommentDto();

        dto.setId(entity.getUuid());
        dto.setCreationDate(entity.getCreationDate());
        dto.setEditDate(entity.getEditDate());
        dto.setText(entity.getText());
        dto.setUser(entity.getUser().getUuid());

        return dto;

    }

}
