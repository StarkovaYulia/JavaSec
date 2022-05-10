package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @GetMapping("/")
    public List<UsersDto> getAll() {
        return usersService.getAll();
    }

    @GetMapping("/{id}")
    public UsersDto getById(@PathVariable String id) {
        return usersService.getById(id);
    }

    @PostMapping("/import")
    public List<UsersDto> importFromCsv() {
        return usersService.saveFromCsv();
    }

    @PostMapping
    public UsersDto create(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return usersService.createOrUpdate(createUpdateUserDto, null);
    }

    @PutMapping("/{id}")
    public UsersDto update(@PathVariable String id, @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return usersService.createOrUpdate(createUpdateUserDto, id);
    }

    @DeleteMapping("/{id}")
    public void update(@PathVariable String id) {
        usersService.delete(id);
    }
}