package com.example.libraryservice.repository;

import com.example.libraryservice.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Long> {
}
