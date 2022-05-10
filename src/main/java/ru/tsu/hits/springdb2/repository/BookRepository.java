package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.tsu.hits.springdb2.entity.AuthorEntity;
import ru.tsu.hits.springdb2.entity.BookEntity;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, String> {

    List<BookEntity> findByAuthor(AuthorEntity authorEntity);
    @NonNull
    List<BookEntity> findAll();
}
