package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/")
    public List<CommentDto> getAll() {
        return commentService.getAll();
    }
    @GetMapping("/{id}")
    public CommentDto getById(@PathVariable String id) {
        return commentService.getById(id);
    }

    @PostMapping
    public CommentDto create(@RequestBody CreateUpdateCommentDto dto) {
        return commentService.createOrUpdate(dto, null);
    }

    @PutMapping("/{id}")
    public CommentDto update(@PathVariable String id, @RequestBody CreateUpdateCommentDto dto) {
        return commentService.createOrUpdate(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commentService.delete(id);
    }
}