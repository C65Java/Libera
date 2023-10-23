package com.example.libraryservice.dao;

import com.example.libraryservice.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.BookView.Get.class)
    @Column(name = "id")
    private Long id;

    @JsonView({Views.BookView.Get.class, Views.BookView.Post.class, Views.BookView.Put.class})
    @Column(name = "isbn")
    private String isbn;

    @JsonView(Views.BookView.Get.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private LibraryUser user;

    @JsonView({Views.BookView.Get.class, Views.BookView.Post.class, Views.BookView.Put.class})
    @Column(name = "book_name")
    private String bookName;

    @JsonView(Views.BookView.Get.class)
    @ManyToMany
    private List<Author> authorList;
}
