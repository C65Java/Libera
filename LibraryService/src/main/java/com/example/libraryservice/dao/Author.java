package com.example.libraryservice.dao;

import com.example.libraryservice.model.Views;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "authors")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.AuthorView.Get.class)
    @Column(name = "id")
    private Long id;

    @JsonView({Views.AuthorView.Get.class, Views.AuthorView.Post.class, Views.AuthorView.Put.class})
    @Column(name = "first_name")
    private String firstName;

    @JsonView({Views.AuthorView.Get.class, Views.AuthorView.Post.class, Views.AuthorView.Put.class})
    @Column(name = "last_name")
    private String lastName;

    @JsonView(Views.AuthorView.Get.class)
    @ManyToMany(mappedBy = "authorList")
    private List<Book> bookList;

}
