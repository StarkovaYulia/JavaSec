package ru.tsu.hits.springdb2.dto;

import lombok.Data;
import ru.tsu.hits.springdb2.entity.Genre;

import java.util.Date;


@Data
public class CreateUpdateBookDto {

    private String name;

    private String authorId;

    private Date releaseDate;

    private Genre genre;

}
