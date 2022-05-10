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
    public BookDto getById(@PathVariable String id){
        return bookService.getById(id);
    }

    @PostMapping
    public BookDto create(@RequestBody CreateUpdateBookDto dto) {
        return bookService.save(dto);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable String id, @RequestBody CreateUpdateBookDto dto){
        return bookService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        bookService.delete(id);
    }
}

