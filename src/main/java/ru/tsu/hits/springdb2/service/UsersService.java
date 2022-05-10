package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.TaskCsv;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.UsersDto;
import ru.tsu.hits.springdb2.dto.converter.UserDtoConverter;
import ru.tsu.hits.springdb2.entity.*;
import ru.tsu.hits.springdb2.repository.UsersRepository;

import java.io.InputStreamReader;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UserDtoConverter userDtoConverter;

    @Value("${csvPath}")
    private String csvPath;

    private UsersDto createFromCsv(TaskCsv user) throws ParseException {
        var dto = userDtoConverter.convertCsvToDto(user);

        var entity = userDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);

        entity = usersRepository.save(entity);

        return userDtoConverter.convertEntityToDto(entity);
    }

    public UsersEntity getUserEntityById(String id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public UsersDto createOrUpdate(CreateUpdateUserDto dto, String id) {
        if (id == null) id = "";

        var entityOptional = usersRepository.findById(id);

        UsersEntity entity;
        if (entityOptional.isEmpty()) {
            entity = userDtoConverter.convertDtoToEntity(UUID.randomUUID().toString(), dto);
        } else {
            entity = entityOptional.get();
            userDtoConverter.updateEntityFromDto(entity, dto);
        }

        entity = usersRepository.save(entity);

        return userDtoConverter.convertEntityToDto(entity);
    }

    @Transactional
    public void delete(String id) {
        var entity = getUserEntityById(id);
        usersRepository.delete(entity);
    }

    @Transactional
    public List<UsersDto> saveFromCsv() {
        var csvStream = UsersService.class.getResourceAsStream(csvPath);
        var tasks = new CsvToBeanBuilder<TaskCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(TaskCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        return tasks.stream()
                .map(task -> {
                    try {
                        return createFromCsv(task);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsersDto> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(userDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsersDto getById(String id) {
        var entity = getUserEntityById(id);
        return userDtoConverter.convertEntityToDto(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usersRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}