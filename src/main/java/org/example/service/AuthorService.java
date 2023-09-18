package org.example.service;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    AuthorDto create(AuthorCreateDto authorCreateDto);

    AuthorDto update(AuthorDto authorDto);

    boolean delete(Long id);
}
