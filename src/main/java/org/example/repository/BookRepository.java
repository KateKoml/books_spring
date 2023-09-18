package org.example.repository;

import org.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Long, Book> {
    boolean setBookGenre(Long bookId, Integer genreId);
}
