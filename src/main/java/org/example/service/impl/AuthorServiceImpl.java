package org.example.service.impl;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.mapper.AuthorMapper;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDto> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public AuthorDto create(AuthorCreateDto authorCreateDto) {
        Author author = authorMapper.toEntity(authorCreateDto);
        authorRepository.saveAndFlush(author);

        return authorMapper.toDto(author);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        Author updatedAuthor = authorMapper.partialUpdateToEntity(authorDto, author);

        return authorMapper.toDto(authorRepository.saveAndFlush(updatedAuthor));
    }

    @Override
    public boolean delete(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new EntityNotFoundException("This author doesn't exist.");
        } else {
            authorRepository.deleteById(id);
            return true;
        }
    }
}
