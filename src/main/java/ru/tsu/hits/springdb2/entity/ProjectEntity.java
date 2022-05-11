package ru.tsu.hits.springdb2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.DATE)
    @Column
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column
    private Date editDate;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "project")
    private List<TaskEntity> tasks;

}
