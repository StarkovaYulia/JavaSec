package ru.tsu.hits.springdb2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TaskEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.DATE)
    @Column
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column
    private Date editDate;

    @Column(nullable = false)
    private String header;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "userCreator_id")
    private UsersEntity userCreator;

    @ManyToOne
    @JoinColumn(name = "userExecutor_id")
    private UsersEntity userExecutor;

    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column
    private String temporaryMark;

    @ManyToMany (mappedBy = "tasks")
    private List<CommentEntity> comments;

}
