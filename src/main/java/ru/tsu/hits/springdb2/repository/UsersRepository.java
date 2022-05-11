package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tsu.hits.springdb2.entity.UsersEntity;


public interface UsersRepository extends CrudRepository<UsersEntity, String> {
}