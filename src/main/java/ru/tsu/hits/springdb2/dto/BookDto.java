package ru.tsu.hits.springdb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.springdb2.entity.Genre;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private String author;

    private Date releaseDate;

    private Genre genre;

}

