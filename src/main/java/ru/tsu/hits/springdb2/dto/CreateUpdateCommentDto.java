package ru.tsu.hits.springdb2.dto;

import lombok.Data;

import java.util.Date;

@Data

public class CreateUpdateCommentDto {

    private Date creationDate;

    private Date editDate;

    private String user;

    private String tasks;

    private String text;
}
