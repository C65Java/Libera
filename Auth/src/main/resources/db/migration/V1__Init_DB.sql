create sequence hibernate_sequence start 1 increment 1;

create table users (
    id int8 generated by default as identity,
    login varchar(255) not null ,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    role varchar(255) not null,
    primary key (id));