package org.example.service;

import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(BookDto bookDto);

    boolean delete(Long id);
}
