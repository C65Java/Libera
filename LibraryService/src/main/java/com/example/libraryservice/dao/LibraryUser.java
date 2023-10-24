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
@Table(name = "library_users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.LibraryUserView.Get.class)
    @Column(name="id")
    private Long id;

    @JsonView({Views.LibraryUserView.Get.class, Views.LibraryUserView.Post.class, Views.LibraryUserView.Put.class})
    @Column(name = "first_name")
    private String firstName;

    @JsonView({Views.LibraryUserView.Get.class, Views.LibraryUserView.Post.class, Views.LibraryUserView.Put.class})
    @Column(name = "last_name")
    private String lastName;

    @JsonView({Views.LibraryUserView.Get.class, Views.LibraryUserView.Post.class, Views.LibraryUserView.Put.class})
    @Column(name = "address")
    private String address;

    @JsonView(Views.LibraryUserView.Get.class)
    @OneToMany(mappedBy = "user")
    private List<Book> bookList;
}
