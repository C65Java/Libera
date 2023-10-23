package com.example.libraryservice.rest;

import com.example.libraryservice.dao.Book;
import com.example.libraryservice.exceptions.NotFoundException;
import com.example.libraryservice.model.Views;
import com.example.libraryservice.repository.IBookRepository;
import com.example.libraryservice.services.BookService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookRestController {
    private final IBookRepository iBookRepository;
    private final BookService bookService;

    @JsonView(Views.BookViewWithLibraryUserAndAuthor.class)
    @GetMapping
    public List<Book> getAll() {
        return iBookRepository.findAll();
    }

    @JsonView(Views.BookViewWithLibraryUserAndAuthor.class)
    @GetMapping("{id}")
    public Book getOne(@PathVariable Long id) {
        return iBookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @JsonView(Views.BookViewWithLibraryUserAndAuthor.class)
    @PostMapping
    public Book create(@RequestBody @JsonView(Views.BookView.Post.class) Book book) {
        return iBookRepository.save(book);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody @JsonView(Views.BookView.Put.class) Book book,
                                    @PathVariable Long id,
                                    @RequestParam(required = false) Long userId) {
        return bookService.updateBook(book, id, userId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}
