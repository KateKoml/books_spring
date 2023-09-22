package service;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.mapper.AuthorMapper;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.example.service.impl.AuthorServiceImpl;
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
class AuthorServiceTest {
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorMapper authorMapper;

    @Test
    void testFindAllAuthors() {
        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author(1L, "John Doe", 1856));
        mockAuthors.add(new Author(2L, "Joan Doe", 1965));

        when(authorRepository.findAll()).thenReturn(mockAuthors);

        List<AuthorDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new AuthorDto(1L, "John Doe", 1856));
        expectedDtoList.add(new AuthorDto(2L, "Joan Doe", 1965));

        when(authorMapper.toDto(any())).thenAnswer(invocation -> {
            Author author = invocation.getArgument(0);
            return new AuthorDto(author.getId(), author.getFullName(), author.getYearOfBirth());
        });

        List<AuthorDto> actualDtoList = authorService.findAll();
        assertIterableEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindByIdAuthor() {
        Long authorId = 1L;
        Author mockAuthor = new Author(authorId, "John Doe", 1856);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(mockAuthor));

        AuthorDto expectedDto = new AuthorDto(authorId, "John Doe", 1856);

        when(authorMapper.toDto(any())).thenAnswer(invocation -> {
            Author author = invocation.getArgument(0);
            return new AuthorDto(author.getId(), author.getFullName(), author.getYearOfBirth());
        });

        AuthorDto actualDto = authorService.findById(authorId);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testCreateAuthor() {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setFullName("John Doe");
        authorCreateDto.setYearOfBirth(1856);

        Author createdAuthor = new Author(1L, "John Doe", 1856);

        when(authorMapper.toEntity(authorCreateDto)).thenReturn(createdAuthor);
        when(authorRepository.saveAndFlush(any(Author.class))).thenReturn(createdAuthor);
        when(authorMapper.toDto(any(Author.class))).thenReturn(new AuthorDto(1L, "John Doe", 1856));

        AuthorDto createdDto = authorService.create(authorCreateDto);

        assertNotNull(createdDto);
        assertEquals(1L, createdDto.getId());
        assertEquals("John Doe", createdDto.getFullName());
        assertEquals(1856, createdDto.getYearOfBirth());

        verify(authorMapper, times(1)).toEntity(authorCreateDto);
        verify(authorRepository, times(1)).saveAndFlush(createdAuthor);
        verify(authorMapper, times(1)).toDto(createdAuthor);
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorCreateDto authorDto = new AuthorCreateDto();
        Author existingAuthor = new Author();
        Author updatedAuthor = new Author();
        AuthorDto expectedAuthorDto = new AuthorDto();

        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.of(existingAuthor));
        when(authorRepository.saveAndFlush(any(Author.class))).thenReturn(updatedAuthor);

        when(authorMapper.partialUpdateToEntity(authorDto, existingAuthor)).thenReturn(updatedAuthor);
        when(authorMapper.toDto(updatedAuthor)).thenReturn(expectedAuthorDto);

        AuthorDto actualAuthorDto = authorService.update(authorId, authorDto);

        assertEquals(actualAuthorDto, expectedAuthorDto);
    }

    @Test
    void testDeleteAuthor() {
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(new Author(authorId, "John Doe", 1856)));

        boolean result = authorService.delete(authorId);

        assertTrue(result);

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testDeleteNonExistingAuthor() {
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authorService.delete(authorId));

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, never()).deleteById(authorId);
    }
}