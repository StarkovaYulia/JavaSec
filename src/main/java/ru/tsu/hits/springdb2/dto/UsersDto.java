package ru.tsu.hits.springdb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.Role;
import ru.tsu.hits.springdb2.entity.TaskEntity;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    private String id;

    private String fio;

    private String email;

    //private byte[] password;
    private String password;

    private Role role;

    private Date creationDate;

    private Date editDate;

    //private List<TaskEntity> tasks;


}