package com.example.libraryservice.services;

import com.example.libraryservice.dao.Author;
import com.example.libraryservice.dao.Book;
import com.example.libraryservice.exceptions.NotFoundException;
import com.example.libraryservice.repository.IAuthorRepository;
import com.example.libraryservice.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
    private final IAuthorRepository iAuthorRepository;
    private final AuthService authService;
    private final IBookRepository iBookRepository;

    public ResponseEntity<?> updateAuthor(Author author, Long id, Long bookId) {
        Map<Object, Object> response = new HashMap<>();
        String username = authService.getAuthentication().getPrincipal().toString();
        Author authorFromDb = iAuthorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UPDATE -> Author with id = " + id
                + " not found. User request: " + username));

        if (author.getFirstName() != null) {
            authorFromDb.setFirstName(author.getFirstName());
            log.info("The FirstName of the author with id {} has been updated -> {}", id, username);
        }

        if (author.getLastName() != null) {
            authorFromDb.setLastName(author.getLastName());
            log.info("The LastName of the author with id {} has been updated -> {}", id, username);
        }

        if (bookId != null) {
        }

        iAuthorRepository.save(authorFromDb);
        response.put("update_author", "true");
        log.info("Update author with id = {} -> {}", id, username);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteAuthor(Long id) {
        Map<Object, Object> response = new HashMap<>();
        String username = authService.getAuthentication().getPrincipal().toString();
        Author authorFromDb = iAuthorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DELETE -> Author with id = " + id
                        + " not found. User request: " + username));

        iAuthorRepository.delete(authorFromDb);
        response.put("delete_author", "true");
        log.info("Delete author with id = {} -> {}", id, username);

        return ResponseEntity.ok(response);
    }
}
