package com.example.libraryservice.repository;

import com.example.libraryservice.dao.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibraryUserRepository extends JpaRepository<LibraryUser, Long> {
}
