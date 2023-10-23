package com.example.libraryservice.repository;

import com.example.libraryservice.dao.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
}
