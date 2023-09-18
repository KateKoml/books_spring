package org.example.service.impl;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public AuthorDto create(AuthorCreateDto authorCreateDto) {
        return null;
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
