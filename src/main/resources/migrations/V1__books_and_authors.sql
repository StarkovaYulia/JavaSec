create table author
(
    id         varchar(255) not null
        primary key,
    first_name varchar(255),
    last_name  varchar(255)
);

alter table author
    owner to hits;

create table books
(
    id           varchar(255) not null
        primary key,
    genre        varchar(255),
    name         varchar(255),
    release_date date,
    author_id    varchar(255)
        constraint author_id_constraint
            references author
);

alter table books
    owner to hits;

create table users
(
    id            varchar(255) not null
        primary key,
    creation_date date,
    edit_date     date,
    email         varchar(255),
    fio           varchar(255),
    password      bytea,
    role          varchar(255)
);

alter table users
    owner to hits;

