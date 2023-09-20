package org.example.controller;

import org.example.dto.GenreCreateDto;
import org.example.dto.GenreDto;
import org.example.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> findAll() {
        List<GenreDto> genreDtos = genreService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(genreDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> findGenreById(@PathVariable Long id) {
        GenreDto genreDto = genreService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(genreDto);
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreCreateDto genreCreateDto) {
        GenreDto createdGenre = genreService.create(genreCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id,
                                                @RequestBody GenreCreateDto genreDto) {
        GenreDto genre = genreService.update(id, genreDto);
        return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteGenreById(@PathVariable Long id) {
        boolean result = genreService.delete(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Genre successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre wasn't found");
        }
    }
}
