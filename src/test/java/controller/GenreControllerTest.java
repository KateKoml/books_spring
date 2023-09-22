package controller;

import org.example.controller.GenreController;
import org.example.dto.GenreCreateDto;
import org.example.dto.GenreDto;
import org.example.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreControllerTest {
    @InjectMocks
    GenreController genreController;

    @Mock
    GenreService genreService;

    @Test
    void testFindAllGenres() {
        GenreDto genreDto = new GenreDto();
        GenreDto genreDto1 = new GenreDto();

        List<GenreDto> genreDtos = new ArrayList<>(List.of(genreDto, genreDto1));
        when(genreService.findAll()).thenReturn(genreDtos);

        ResponseEntity<List<GenreDto>> response = genreController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreDtos, response.getBody());

        verify(genreService, times(1)).findAll();
    }

    @Test
    void testFindGenreById() {
        Long genreId = 1L;
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genreId);
        genreDto.setType("horror");

        when(genreService.findById(genreId)).thenReturn(genreDto);

        ResponseEntity<GenreDto> response = genreController.findGenreById(genreId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreDto, response.getBody());

        verify(genreService, times(1)).findById(genreId);
    }

    @Test
    void testCreateGenre() {
        GenreCreateDto genreCreateDto = new GenreCreateDto();
        genreCreateDto.setType("horror");

        GenreDto createdGenre = new GenreDto();
        createdGenre.setId(1L);
        createdGenre.setType(genreCreateDto.getType());

        when(genreService.create(genreCreateDto)).thenReturn(createdGenre);

        ResponseEntity<GenreDto> response = genreController.createGenre(genreCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdGenre, response.getBody());

        verify(genreService, times(1)).create(genreCreateDto);
    }

    @Test
    void testUpdateGenre() {
        Long genreId = 1L;
        GenreCreateDto genreCreateDto = new GenreCreateDto();
        genreCreateDto.setType("horror");

        GenreDto updatedGenre = new GenreDto();
        updatedGenre.setId(genreId);
        updatedGenre.setType(genreCreateDto.getType());

        when(genreService.update(genreId, genreCreateDto)).thenReturn(updatedGenre);

        ResponseEntity<GenreDto> response = genreController.updateGenre(genreId, genreCreateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGenre, response.getBody());

        verify(genreService, times(1)).update(genreId, genreCreateDto);
    }

    @Test
    void testDeleteGenreById() {
        Long genreId = 1L;

        when(genreService.delete(genreId)).thenReturn(true);

        ResponseEntity<String> response = genreController.deleteGenreById(genreId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Genre successfully deleted", response.getBody());

        verify(genreService, times(1)).delete(genreId);
    }
}
