package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.RegisterDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.repository.UsersRepository;
import ru.tsu.hits.springdb2.service.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsersService usersService;

    @PostMapping("/login")
    public List<String> login(Authentication authentication) {
        return authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
    }

    @PostMapping("/register")
    public UsersDto register(@RequestBody RegisterDto dto) {
        return usersService.createNew(dto);
    }

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }
}
