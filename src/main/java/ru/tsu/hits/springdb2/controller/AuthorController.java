package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/")
    public List<AuthorDto> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping(value = "/{id}")
    public AuthorDto getAuthor(@PathVariable String id) {
        return authorService.getAuthor(id);
    }

    @PostMapping
    public AuthorDto createAuthor(@RequestBody CreateUpdateAuthorDto dto) {
        return authorService.createOrUpdate(dto, null);
    }

    @PutMapping(value = "/{id}")
    public AuthorDto createAuthor(@PathVariable String id, @RequestBody CreateUpdateAuthorDto dto) {
        return authorService.createOrUpdate(dto, id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
    }
}
