package org.example.service;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(Long id);

    AuthorDto create(AuthorCreateDto authorCreateDto);

    AuthorDto update(Long id, AuthorCreateDto authorDto);

    boolean delete(Long id);
}
