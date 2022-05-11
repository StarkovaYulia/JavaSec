package ru.tsu.hits.springdb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;

import java.util.List;

public interface ProjectRepository extends CrudRepository<ProjectEntity,String> {
    @NonNull
    List<ProjectEntity> findAll();
}
