package org.example.repository;

import org.example.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Long, Genre> {
    Optional<Genre> findById(Long id);
}
