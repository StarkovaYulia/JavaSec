package ru.tsu.hits.springdb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String id;

    private Date creationDate;

    private Date editDate;

    private String user;

    private String text;
}
