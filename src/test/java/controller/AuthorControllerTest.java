package controller;

import org.example.controller.AuthorController;
import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.service.AuthorService;
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
class AuthorControllerTest {
    @InjectMocks
    AuthorController authorController;
    @Mock
    AuthorService authorService;

    @Test
    void testFindAllAuthors() {
        AuthorDto authorDto = new AuthorDto();
        AuthorDto authorDto1 = new AuthorDto();

        List<AuthorDto> authorDtos = new ArrayList<>(List.of(authorDto, authorDto1));
        when(authorService.findAll()).thenReturn(authorDtos);

        ResponseEntity<List<AuthorDto>> response = authorController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorDtos, response.getBody());

        verify(authorService, times(1)).findAll();
    }

    @Test
    void testFindAuthorById() {
        Long authorId = 1L;
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(authorId);
        authorDto.setFullName("John Doe");
        authorDto.setYearOfBirth(1856);

        when(authorService.findById(authorId)).thenReturn(authorDto);

        ResponseEntity<AuthorDto> response = authorController.findAuthorById(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorDto, response.getBody());

        verify(authorService, times(1)).findById(authorId);
    }

    @Test
    void testCreateAuthor() {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setFullName("John Doe");
        authorCreateDto.setYearOfBirth(1856);

        AuthorDto createdAuthor = new AuthorDto();
        createdAuthor.setId(1L);
        createdAuthor.setFullName(authorCreateDto.getFullName());
        createdAuthor.setYearOfBirth(authorCreateDto.getYearOfBirth());

        when(authorService.create(authorCreateDto)).thenReturn(createdAuthor);

        ResponseEntity<AuthorDto> response = authorController.createAuthor(authorCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdAuthor, response.getBody());

        verify(authorService, times(1)).create(authorCreateDto);
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setFullName("John Doe");
        authorCreateDto.setYearOfBirth(1856);

        AuthorDto updatedAuthor = new AuthorDto();
        updatedAuthor.setId(authorId);
        updatedAuthor.setFullName(authorCreateDto.getFullName());
        updatedAuthor.setYearOfBirth(authorCreateDto.getYearOfBirth());

        when(authorService.update(authorId, authorCreateDto)).thenReturn(updatedAuthor);

        ResponseEntity<AuthorDto> response = authorController.updateAuthor(authorId, authorCreateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAuthor, response.getBody());

        verify(authorService, times(1)).update(authorId, authorCreateDto);
    }

    @Test
    void testDeleteAuthorById() {
        Long authorId = 1L;

        when(authorService.delete(authorId)).thenReturn(true);

        ResponseEntity<String> response = authorController.deleteAuthorById(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author successfully deleted", response.getBody());

        verify(authorService, times(1)).delete(authorId);
    }
}
