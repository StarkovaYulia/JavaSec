package ru.tsu.hits.springdb2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;


    @Enumerated(EnumType.STRING)
    @Column
    private Genre genre;


}