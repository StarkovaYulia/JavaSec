package ru.tsu.hits.springdb2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @PostMapping("/login")
    public List<String> login(Authentication authentication) {
        return authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
    }

    @PostMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }
}
