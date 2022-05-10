package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, String> {
    @NonNull
    List<CommentEntity> findAll();
    List<CommentEntity> findByTasks(TaskEntity taskEntity);
    List<CommentEntity> findByUser(UsersEntity usersEntity);

}
