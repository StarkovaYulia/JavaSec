package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookDto save(@RequestBody CreateUpdateBookDto createUpdateBookDto) {
        return bookService.save(createUpdateBookDto);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable UUID id){
        return bookService.getById(id.toString());
    }

}

