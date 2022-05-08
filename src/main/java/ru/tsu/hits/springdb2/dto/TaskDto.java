package ru.tsu.hits.springdb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.springdb2.entity.Priority;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String id;

    private Date creationDate;

    private Date editDate;

    private String header;

    private String description;

    private Priority priority;

    private String temporaryMark;

    private String userCreator;

    private String userExecutor;

    private String project;

    private List<CommentDto> comments;

}
