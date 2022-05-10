package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.BookDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateBookDto;
import ru.tsu.hits.springdb2.service.BookService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public List<BookDto> getAll(){
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable UUID id){
        return bookService.getById(id.toString());
    }

    @PostMapping
    public BookDto create(@RequestBody CreateUpdateBookDto createUpdateBookDto) {
        return bookService.save(createUpdateBookDto);
    }
}

