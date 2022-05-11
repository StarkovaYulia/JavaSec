package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.service.UsersService;

@RestController
@RequestMapping("/usersCsv")
@RequiredArgsConstructor

public class UserCsvController {

    private final UsersService usersService;

    @PostMapping
    public String save(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        usersService.saveFromCsv();
        return "Csv users in Database now";
    }
}
