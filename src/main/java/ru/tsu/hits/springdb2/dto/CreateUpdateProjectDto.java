package ru.tsu.hits.springdb2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateUpdateProjectDto {

    private Date creationDate;

    private Date editDate;

    private String name;

    private String description;
}
