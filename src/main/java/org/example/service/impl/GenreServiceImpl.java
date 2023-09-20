package org.example.service.impl;

import org.example.dto.GenreCreateDto;
import org.example.dto.GenreDto;
import org.example.mapper.GenreMapper;
import org.example.model.Genre;
import org.example.repository.GenreRepository;
import org.example.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public GenreDto findById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This genre doesn't exist."));
        return genreMapper.toDto(genre);
    }

    @Transactional
    @Override
    public GenreDto create(GenreCreateDto genreCreateDto) {
        Genre genre = genreMapper.toEntity(genreCreateDto);
        return genreMapper.toDto(genreRepository.saveAndFlush(genre));
    }

    @Transactional
    @Override
    public GenreDto update(Long id, GenreCreateDto genreDto) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This genre doesn't exist."));
        Genre updatedGenre = genreMapper.partialUpdateToEntity(genreDto, genre);

        return genreMapper.toDto(genreRepository.saveAndFlush(updatedGenre));
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            throw new EntityNotFoundException("This genre doesn't exist.");
        } else {
            genreRepository.deleteById(id);
            return true;
        }
    }
}
