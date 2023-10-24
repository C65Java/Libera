package com.example.libraryservice.rest;

import com.example.libraryservice.dao.LibraryUser;
import com.example.libraryservice.exceptions.NotFoundException;
import com.example.libraryservice.model.Views;
import com.example.libraryservice.repository.ILibraryUserRepository;
import com.example.libraryservice.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController {
    private final ILibraryUserRepository iLibraryUserRepository;
    private final UserService userService;

    @JsonView(Views.LibraryUserViewWithBook.class)
    @GetMapping
    public List<LibraryUser> getAll() {
        return iLibraryUserRepository.findAll();
    }

    @JsonView(Views.LibraryUserViewWithBook.class)
    @GetMapping("{id}")
    public LibraryUser getOne(@PathVariable Long id) {
        return iLibraryUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @JsonView(Views.LibraryUserViewWithBook.class)
    @PostMapping
    public LibraryUser create(@RequestBody @JsonView(Views.LibraryUserView.Post.class) LibraryUser libraryUser) {
        return iLibraryUserRepository.save(libraryUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody @JsonView(Views.LibraryUserView.Put.class) LibraryUser libraryUser,
                                    @PathVariable Long id) {
        return userService.updateLibraryUser(libraryUser, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.deleteLibraryUser(id);
    }
}
