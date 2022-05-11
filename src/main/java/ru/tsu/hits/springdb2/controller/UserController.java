package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.entity.Role;
import ru.tsu.hits.springdb2.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/")
    public List<UsersDto> getAll() {
        return usersService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public UsersDto getById(@PathVariable String id) {
        return usersService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/import")
    public List<UsersDto> importFromCsv() {
        return usersService.saveFromCsv();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public UsersDto create(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return usersService.createOrUpdate(createUpdateUserDto, null);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public UsersDto update(@PathVariable String id, @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return usersService.createOrUpdate(createUpdateUserDto, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/role/{id}")
    public UsersDto updateRole(@PathVariable String id) {
        return usersService.updateRole(id, Role.ADMIN);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void update(@PathVariable String id) {
        usersService.delete(id);
    }
}