package ru.tsu.hits.springdb2.dto;

import lombok.Data;
import ru.tsu.hits.springdb2.entity.Role;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.crypto.KeySelector;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Date;

@Data
public class CreateUpdateUserDto {

    private String fio;

    private String email;

    private String password;

    private Role role;

    private Date creationDate;

    private Date editDate;

}
