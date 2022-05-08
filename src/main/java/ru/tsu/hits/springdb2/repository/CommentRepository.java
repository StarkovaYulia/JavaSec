package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, String> {

    List<CommentEntity> findByTasks(TaskEntity taskEntity);
    List<CommentEntity> findByUser(UsersEntity usersEntity);

}
