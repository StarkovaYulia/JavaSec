package ru.tsu.hits.springdb2.dto;

import lombok.Data;
import org.springframework.scheduling.config.Task;

import java.util.Date;
import java.util.List;

@Data

public class CreateUpdateCommentDto {

    private Date creationDate;

    private Date editDate;

    private String user;

    private String tasks;

}
