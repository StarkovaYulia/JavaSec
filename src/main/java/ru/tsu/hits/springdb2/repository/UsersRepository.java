package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.List;


public interface UsersRepository extends CrudRepository<UsersEntity, String> {
    @NonNull
    List<UsersEntity> findAll();
}