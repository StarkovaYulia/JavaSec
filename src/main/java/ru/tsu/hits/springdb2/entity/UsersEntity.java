package ru.tsu.hits.springdb2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Column
    private String fio;

    @Column
    private String email;

    @Column
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Temporal(TemporalType.DATE)
    @Column
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column
    private Date editDate;

    @OneToMany( mappedBy = "userCreator")
    private List<TaskEntity> createdTasks;

    @OneToMany( mappedBy = "userExecutor")
    private List<TaskEntity> executionTasks;

    @OneToMany( mappedBy = "user")
    private List<CommentEntity> comments;

}

