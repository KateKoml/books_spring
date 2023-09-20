package org.example.service.impl;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.mapper.AuthorMapper;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AuthorDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This author doesn't exist."));
        return authorMapper.toDto(author);
    }

    @Transactional
    @Override
    public AuthorDto create(AuthorCreateDto authorCreateDto) {
        Author author = authorMapper.toEntity(authorCreateDto);
        return authorMapper.toDto(authorRepository.saveAndFlush(author));
    }

    @Transactional
    @Override
    public AuthorDto update(Long id, AuthorCreateDto authorDto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This author doesn't exist."));
        Author updatedAuthor = authorMapper.partialUpdateToEntity(authorDto, author);

        return authorMapper.toDto(authorRepository.saveAndFlush(updatedAuthor));
    }

    @Transactional
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
