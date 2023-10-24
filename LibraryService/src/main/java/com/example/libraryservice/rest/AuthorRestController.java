package com.example.libraryservice.rest;

import com.example.libraryservice.dao.Author;
import com.example.libraryservice.exceptions.NotFoundException;
import com.example.libraryservice.model.Views;
import com.example.libraryservice.repository.IAuthorRepository;
import com.example.libraryservice.services.AuthorService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorRestController {
    private final IAuthorRepository iAuthorRepository;
    private final AuthorService authorService;

    @JsonView(Views.AuthorViewWithBook.class)
    @GetMapping
    public List<Author> getAll() {
        return iAuthorRepository.findAll();
    }

    @JsonView(Views.AuthorViewWithBook.class)
    @GetMapping("{id}")
    public Author getOne(@PathVariable Long id) {
        return iAuthorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author not found"));
    }

    @JsonView(Views.AuthorViewWithBook.class)
    @PostMapping
    public Author create(@RequestBody @JsonView(Views.AuthorView.Post.class) Author author) {
        return iAuthorRepository.save(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody @JsonView(Views.AuthorView.Put.class) Author author,
                                    @PathVariable Long id,
                                    @RequestParam(required = false) Long bookId) {
        return authorService.updateAuthor(author, id, bookId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }
}
