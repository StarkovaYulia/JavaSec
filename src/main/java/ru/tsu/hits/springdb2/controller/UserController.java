package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.service.UsersService;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PostMapping
    public UsersDto save(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        //usersService.saveFromCsv();
        return usersService.save(createUpdateUserDto);
    }


    @GetMapping("/{id}")
    public UsersDto getById(@PathVariable UUID id)
    {
        return usersService.getById(id.toString());
    }

}