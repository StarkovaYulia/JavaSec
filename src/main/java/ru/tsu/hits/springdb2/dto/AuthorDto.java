package ru.tsu.hits.springdb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String id;

    private String firstName;

    private String lastName;

    private List<BookDto> books;

}