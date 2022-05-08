package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.service.AuthorService;
import ru.tsu.hits.springdb2.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public AuthorDto createAuthor(@RequestBody CreateUpdateAuthorDto createUpdateAuthorDto) {
        return authorService.createAuthor(createUpdateAuthorDto);
    }

    @GetMapping(value = "/{id}")
    public AuthorDto getAuthor(@PathVariable String id) {
        return authorService.getAuthorDtoById(id);
    }

}