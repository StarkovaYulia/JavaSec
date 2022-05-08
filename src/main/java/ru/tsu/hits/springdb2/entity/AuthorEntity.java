package ru.tsu.hits.springdb2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorEntity {

    @Id
    @Column (name = "id")
    private String uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany( mappedBy = "author" )
    private List<BookEntity> books;

}