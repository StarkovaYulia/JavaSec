package ru.tsu.hits.springdb2.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String fio;

    private String email;

    private String password;
}
