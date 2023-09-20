package org.example.service;

import org.example.dto.BookDto;
import org.example.dto.GenreCreateDto;
import org.example.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(Long id);

    GenreDto create(GenreCreateDto genreCreateDto);

    GenreDto update(Long id, GenreCreateDto genreDto);

    boolean delete(Long id);
}
