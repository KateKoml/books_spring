package controller;

import org.example.controller.BookController;
import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.model.Author;
import org.example.service.BookService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Test
    void testFindAllBooks() {
        BookDto bookDto = new BookDto();
        BookDto bookDto1 = new BookDto();

        List<BookDto> bookDtos = new ArrayList<>(List.of(bookDto, bookDto1));
        when(bookService.findAll()).thenReturn(bookDtos);

        ResponseEntity<List<BookDto>> response = bookController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDtos, response.getBody());

        verify(bookService, times(1)).findAll();
    }

    @Test
    void testFindBookById() {
        Long bookId = 1L;
        Author author = new Author(1L, "John Doe", 1856);

        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setName("Hello world");
        bookDto.setYear(1888);
        bookDto.setAuthorId(author.getId());

        when(bookService.findById(bookId)).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.findBookById(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());

        verify(bookService, times(1)).findById(bookId);
    }

    @Test
    void testCreateBook() {
        Author author = new Author(1L, "John Doe", 1856);
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Hello world");
        bookCreateDto.setYear(1888);
        bookCreateDto.setAuthor(author.getId());

        BookDto createdBook = new BookDto();
        createdBook.setId(1L);
        createdBook.setName(bookCreateDto.getName());
        createdBook.setYear(bookCreateDto.getYear());
        createdBook.setAuthorId(author.getId());

        when(bookService.create(bookCreateDto)).thenReturn(createdBook);

        ResponseEntity<BookDto> response = bookController.createBook(bookCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdBook, response.getBody());

        verify(bookService, times(1)).create(bookCreateDto);
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Author author = new Author(1L, "John Doe", 1856);

        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Hello world");
        bookCreateDto.setYear(1888);
        bookCreateDto.setAuthor(author.getId());

        BookDto updatedBook = new BookDto();
        updatedBook.setId(bookId);
        updatedBook.setName(bookCreateDto.getName());
        updatedBook.setYear(bookCreateDto.getYear());
        updatedBook.setAuthorId(author.getId());

        when(bookService.update(bookId, bookCreateDto)).thenReturn(updatedBook);

        ResponseEntity<BookDto> response = bookController.updateBook(bookId, bookCreateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());

        verify(bookService, times(1)).update(bookId, bookCreateDto);
    }

    @Test
    void testDeleteBookById() {
        Long bookId = 1L;

        when(bookService.delete(bookId)).thenReturn(true);

        ResponseEntity<String> response = bookController.deleteBookById(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book successfully deleted", response.getBody());

        verify(bookService, times(1)).delete(bookId);
    }

    @Test
    void testAddBookGenre() {
        Long bookId = 1L;
        Long genreId = 1L;

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK)
                .body("Now book with id: " + bookId + " has genre with id: " + genreId);

        doNothing().when(bookService).setBookGenre(bookId, genreId);

        ResponseEntity<String> response = bookController.addBookGenre(bookId, genreId);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(bookService, times(1)).setBookGenre(bookId, genreId);
    }

    @Test
    void testFindBookByGenreId() {
        Long genreId = 1L;

        List<BookDto> expectedBooks = new ArrayList<>();

        ResponseEntity<List<BookDto>> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(expectedBooks);

        when(bookService.findByGenreId(genreId)).thenReturn(expectedBooks);

        ResponseEntity<List<BookDto>> response = bookController.findBookByGenreId(genreId);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(bookService, times(1)).findByGenreId(genreId);
    }
}
