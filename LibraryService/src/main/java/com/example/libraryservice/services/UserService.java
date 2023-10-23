package com.example.libraryservice.services;

import com.example.libraryservice.dao.LibraryUser;
import com.example.libraryservice.exceptions.NotFoundException;
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
public class UserService {
    private final ILibraryUserRepository iUserRepository;
    private final AuthService authService;

    public ResponseEntity<?> updateLibraryUser(LibraryUser libraryUser, Long id) {
        Map<Object, Object> response = new HashMap<>();
        String username = authService.getAuthentication().getPrincipal().toString();
        LibraryUser libraryUserFromDb = iUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UPDATE -> LibraryUser with id = " + id
                + " not found. User request: " + username));

        if (libraryUser.getFirstName() != null) {
            libraryUserFromDb.setFirstName(libraryUser.getFirstName());
            log.info("The FirstName of the LibraryUser with id {} has been updated -> {}", id, username);
        }

        if (libraryUser.getLastName() != null) {
            libraryUserFromDb.setLastName(libraryUser.getLastName());
            log.info("The LastName of the LibraryUser with id {} has been updated -> {}", id, username);
        }

        if (libraryUser.getAddress() != null) {
            libraryUserFromDb.setAddress(libraryUser.getAddress());
            log.info("The address of the LibraryUser with id {} has been updated -> {}", id, username);
        }

        iUserRepository.save(libraryUserFromDb);
        response.put("update_library_user", "true");
        log.info("Update LibraryUser with id = {} -> {}", id, username);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteLibraryUser(Long id) {
        Map<Object, Object> response = new HashMap<>();
        String username = authService.getAuthentication().getPrincipal().toString();
        LibraryUser libraryUserFromDb = iUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DELETE -> LibraryUser with id = " + id
                        + " not found. User request: " + username));

        iUserRepository.delete(libraryUserFromDb);
        response.put("delete_library_user", "true");
        log.info("Delete LibraryUser with id = {} -> {}", id, username);

        return ResponseEntity.ok(response);
    }
}
