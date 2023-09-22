package service;

import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.mapper.BookMapper;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.example.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    void testFindAllBooks() {
        List<Book> books = new ArrayList<>();
        Author author = new Author(1L, "John Doe", 1856);
        books.add(new Book(1L, "Hello world", 1888, author));
        books.add(new Book(2L, "Spring boot", 1891, author));

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new BookDto(1L, "Hello world", 1888, author.getId()));
        expectedDtoList.add(new BookDto(2L, "Spring boot", 1891, author.getId()));

        when(bookMapper.toDto(any())).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return new BookDto(book.getId(), book.getName(), book.getYear(), book.getAuthor().getId());
        });

        List<BookDto> actualDtoList = bookService.findAll();
        assertIterableEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindByIdBook() {
        Long bookId = 1L;
        Author author = new Author(1L, "John Doe", 1856);
        Book mockBook = new Book(bookId, "Hello world", 1888, author);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        BookDto expectedDto = new BookDto(bookId, "Hello world", 1888, author.getId());

        when(bookMapper.toDto(any())).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return new BookDto(book.getId(), book.getName(), book.getYear(), book.getAuthor().getId());
        });

        BookDto actualDto = bookService.findById(bookId);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testCreateBook() {
        Author author = new Author(1L, "John Doe", 1856);
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Hello world");
        bookCreateDto.setYear(1888);
        bookCreateDto.setAuthor(author.getId());

        Book createdBook = new Book(1L, "Hello world", 1888, author);

        when(bookMapper.toEntity(bookCreateDto)).thenReturn(createdBook);
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(createdBook);
        when(bookMapper.toDto(any(Book.class))).thenReturn(new BookDto(1L, "Hello world", 1888, author.getId()));

        BookDto createdDto = bookService.create(bookCreateDto);

        assertNotNull(createdDto);
        assertEquals(1L, createdDto.getId());
        assertEquals("Hello world", createdDto.getName());
        assertEquals(1888, createdDto.getYear());

        verify(bookMapper, times(1)).toEntity(bookCreateDto);
        verify(bookRepository, times(1)).saveAndFlush(createdBook);
        verify(bookMapper, times(1)).toDto(createdBook);
    }

    @Test
    void testUpdateAuthor() {
        Long bookId = 1L;
        BookCreateDto bookCreateDto = new BookCreateDto();
        Book existingBook = new Book();
        Book updatedBook = new Book();
        BookDto expectedBookDto = new BookDto();

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(existingBook));
        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(updatedBook);

        when(bookMapper.partialUpdateToEntity(bookCreateDto, existingBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedBookDto);

        BookDto actualBookDto = bookService.update(bookId, bookCreateDto);

        assertEquals(actualBookDto, expectedBookDto);
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        Author author = new Author(1L, "John Doe", 1856);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book(bookId, "Hello world", 1888, author)));

        boolean result = bookService.delete(bookId);

        assertTrue(result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testDeleteNonExistingBook() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.delete(bookId));

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).deleteById(bookId);
    }

    @Test
    void testSetBookGenre() {
        Long bookId = 1L;
        Long genreId = 2L;
        Author author = new Author(1L, "John Doe", 1856);

        Book book = new Book();
        book.setId(bookId);
        book.setName("Hello world");
        book.setYear(1888);
        book.setAuthor(author);
        book.setGenres(new HashSet<>());

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setType("horror");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        bookService.setBookGenre(bookId, genreId);

        assertTrue(book.getGenres().contains(genre));
        verify(bookRepository, times(1)).saveAndFlush(book);
    }

    @Test
    void testSetBookGenreWithNonExistingBook() {
        Long bookId = 1L;
        Long genreId = 2L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.setBookGenre(bookId, genreId));
        verify(genreRepository, never()).findById(anyLong());
    }

    @Test
    void testFindByGenreId() {
        Long genreId = 1L;
        Author author = new Author(1L, "John Doe", 1856);

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setType("horror");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("Hello world");
        book1.setYear(1888);
        book1.setAuthor(author);
        book1.setGenres(Set.of(genre));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Spring boot");
        book2.setYear(1905);
        book2.setAuthor(author);
        book2.setGenres(Set.of(genre));

        List<Book> books = List.of(book1, book2);

        when(bookRepository.findByGenreId(genreId)).thenReturn(books);
        when(bookMapper.toDto(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return new BookDto(book.getId(), book.getName(), book.getYear(), book.getAuthor().getId());
        });

        List<BookDto> result = bookService.findByGenreId(genreId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hello world", result.get(0).getName());
        assertEquals("Spring boot", result.get(1).getName());
    }

    @Test
    void testFindByGenreIdWithNoBooks() {
        Long genreId = 1L;

        when(bookRepository.findByGenreId(genreId)).thenReturn(new ArrayList<>());

        List<BookDto> result = bookService.findByGenreId(genreId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
