package com.example.libraryservice.services;

import com.example.libraryservice.dao.Book;
import com.example.libraryservice.exceptions.NotFoundException;
import com.example.libraryservice.repository.IBookRepository;
import com.example.libraryservice.repository.ILibraryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final IBookRepository iBookRepository;
    private final ILibraryUserRepository iLibraryUserRepository;
    private final AuthService authService;

    public ResponseEntity<?> updateBook(Book book, Long id, Long userId) {
        Map<Object, Object> response = new HashMap<>();
        String username = authService.getAuthentication().getPrincipal().toString();
        Book bookFromDB = iBookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UPDATE -> Book with id = " + id
                        + " not found. User request: " + username));

        if (book.getIsbn() != null) {
            bookFromDB.setIsbn(book.getIsbn());
            log.info("The ISBN of the book with id {} has been updated -> {}", id, username);
        }

        if (userId != null) {
            bookFromDB.setUser(iLibraryUserRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("UPDATE -> User with id = " + userId
                    + " not found. User request: " + username)));
            log.info("The Library user of the book with id {} has been updated -> {}", id, username);
        }

        if (book.getBookName() !=null) {
            bookFromDB.setBookName(book.getBookName());
            log.info("The Book Name of the book with id {} has been updated -> {}", id, username);
        }

        iBookRepository.save(bookFromDB);
        response.put("update_book", "true");
        log.info("Update book with id = {} -> {}", id, username);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteBook(Long id) {
        Map<Object, Object> response = new HashMap<>();
        String username = authService.getAuthentication().getPrincipal().toString();
        Book bookFromDb = iBookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DELETE -> Book with id = " + id
                + " not found. User request: " + username));

        iBookRepository.delete(bookFromDb);
        response.put("delete_book", "true");
        log.info("Delete book with id = {} -> {}", id, username);

        return ResponseEntity.ok(response);
    }
}
