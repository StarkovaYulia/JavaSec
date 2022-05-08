package ru.tsu.hits.springdb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.tsu.hits.springdb2.entity.AuthorEntity;

public interface AuthorRepository extends CrudRepository<AuthorEntity, String> {
}