package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.tsu.hits.springdb2.entity.AuthorEntity;

import java.util.List;

public interface AuthorRepository extends CrudRepository<AuthorEntity, String> {
    @NonNull
    List<AuthorEntity> findAll();
}