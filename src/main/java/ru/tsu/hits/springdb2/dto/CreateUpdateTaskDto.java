package ru.tsu.hits.springdb2.dto;

import lombok.Data;
import ru.tsu.hits.springdb2.entity.Priority;

import java.util.Date;

@Data
public class CreateUpdateTaskDto {

    private Date creationDate;

    private Date editDate;

    private String header;

    private String description;

    private Priority priority;

    private String temporaryMark;

    private String project;

    private String userCreator;

    private String userExecutor;

}
