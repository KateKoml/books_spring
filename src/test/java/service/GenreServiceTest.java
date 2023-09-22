package service;

import org.example.dto.GenreCreateDto;
import org.example.dto.GenreDto;
import org.example.mapper.GenreMapper;
import org.example.model.Genre;
import org.example.repository.GenreRepository;
import org.example.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {
    @InjectMocks
    private GenreServiceImpl genreService;

    @Mock
    GenreRepository genreRepository;

    @Mock
    GenreMapper genreMapper;

    @Test
    void testFindAllGenres() {
        List<Genre> mockGenre = new ArrayList<>();
        mockGenre.add(new Genre(1L, "horror"));
        mockGenre.add(new Genre(2L, "romance"));

        when(genreRepository.findAll()).thenReturn(mockGenre);

        List<GenreDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new GenreDto(1L, "horror"));
        expectedDtoList.add(new GenreDto(2L, "romance"));

        when(genreMapper.toDto(any())).thenAnswer(invocation -> {
            Genre genre = invocation.getArgument(0);
            return new GenreDto(genre.getId(), genre.getType());
        });

        List<GenreDto> actualDtoList = genreService.findAll();
        assertIterableEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindByIdGenre() {
        Long genreId = 1L;
        Genre mockGenre = new Genre(genreId, "horror");

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(mockGenre));

        GenreDto expectedDto = new GenreDto(genreId, "horror");

        when(genreMapper.toDto(any())).thenAnswer(invocation -> {
            Genre genre = invocation.getArgument(0);
            return new GenreDto(genre.getId(), genre.getType());
        });

        GenreDto genreDto = genreService.findById(genreId);
        assertEquals(expectedDto, genreDto);
    }

    @Test
    void testCreateGenre() {
        GenreCreateDto genreCreateDto = new GenreCreateDto();
        genreCreateDto.setType("horror");

        Genre createdGenre = new Genre(1L, "horror");

        when(genreMapper.toEntity(genreCreateDto)).thenReturn(createdGenre);
        when(genreRepository.saveAndFlush(any(Genre.class))).thenReturn(createdGenre);
        when(genreMapper.toDto(any(Genre.class))).thenReturn(new GenreDto(1L, "horror"));

        GenreDto createdDto = genreService.create(genreCreateDto);

        assertNotNull(createdDto);
        assertEquals(1L, createdDto.getId());
        assertEquals("horror", createdDto.getType());

        verify(genreMapper, times(1)).toEntity(genreCreateDto);
        verify(genreRepository, times(1)).saveAndFlush(createdGenre);
        verify(genreMapper, times(1)).toDto(createdGenre);
    }

    @Test
    void testUpdateGenre() {
        Long genreId = 1L;
        GenreCreateDto genreCreateDto = new GenreCreateDto();
        Genre existingGenre = new Genre();
        Genre updatedGenre = new Genre();
        GenreDto expectedGenreDto = new GenreDto();

        when(genreRepository.findById(genreId)).thenReturn(java.util.Optional.of(existingGenre));
        when(genreRepository.saveAndFlush(any(Genre.class))).thenReturn(updatedGenre);

        when(genreMapper.partialUpdateToEntity(genreCreateDto, existingGenre)).thenReturn(updatedGenre);
        when(genreMapper.toDto(updatedGenre)).thenReturn(expectedGenreDto);

        GenreDto actualGenreDto = genreService.update(genreId, genreCreateDto);

        assertEquals(actualGenreDto, expectedGenreDto);
    }

    @Test
    void testDeleteGenre() {
        Long genreId = 1L;

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(new Genre(genreId, "horror")));

        boolean result = genreService.delete(genreId);

        assertTrue(result);

        verify(genreRepository, times(1)).findById(genreId);
        verify(genreRepository, times(1)).deleteById(genreId);
    }

    @Test
    void testDeleteNonExistingGenre() {
        Long genreId = 1L;

        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> genreService.delete(genreId));

        verify(genreRepository, times(1)).findById(genreId);
        verify(genreRepository, never()).deleteById(genreId);
    }
}
