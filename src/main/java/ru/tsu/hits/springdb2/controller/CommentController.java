package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.AuthorDto;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateAuthorDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.service.AuthorService;
import ru.tsu.hits.springdb2.service.CommentService;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto createComment(@RequestBody CreateUpdateCommentDto createUpdateCommentDto) {
        return commentService.createComment(createUpdateCommentDto);
    }

    @GetMapping(value = "/{id}")
    public CommentDto getComment(@PathVariable String id) {
        return commentService.getCommentDtoById(id);
    }

}