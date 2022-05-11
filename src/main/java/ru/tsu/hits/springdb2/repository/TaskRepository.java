package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UsersEntity;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, String> {
    @NonNull
    List<TaskEntity> findAll();
    List<TaskEntity> findByUserExecutor(UsersEntity usersEntity);
    List<TaskEntity> findByProject(ProjectEntity projectEntity);
    List<TaskEntity> findByComments(CommentEntity commentEntity);
}
