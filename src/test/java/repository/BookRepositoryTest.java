package repository;

import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(TestConfig.class)
@ExtendWith(PostgresExtension.class)
@Transactional
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testSaveFlushAndFindByIdBook() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.save(author);

        Book book = new Book();
        book.setName("The Raven");
        book.setYear(1845);
        book.setAuthor(author);

        bookRepository.saveAndFlush(book);

        Long bookId = book.getId();
        Optional<Book> optBook = bookRepository.findById(bookId);
        assertTrue(optBook.isPresent());
        assertEquals("The Raven", optBook.get().getName());
    }

    @Test
    void testSaveAllAndFindAllBooks() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.save(author);

        Book book = new Book();
        book.setName("The Raven");
        book.setYear(1845);
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setName("The Tell-Tale Heart");
        book2.setYear(1843);
        book2.setAuthor(author);

        bookRepository.saveAll(List.of(book, book2));

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
        assertEquals("The Raven", books.get(0).getName());
        assertEquals("The Tell-Tale Heart", books.get(1).getName());
    }

    @Test
    void testDeleteBook() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.save(author);

        Book book = new Book();
        book.setName("The Raven");
        book.setYear(1845);
        book.setAuthor(author);

        bookRepository.saveAndFlush(book);

        Long bookId = book.getId();
        assertTrue(bookRepository.existsById(bookId));

        bookRepository.deleteById(bookId);
        assertFalse(bookRepository.existsById(bookId));
    }

    @Test
    void testUpdateBook() {
        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.save(author);

        Book book = new Book();
        book.setName("The Raven");
        book.setYear(1845);
        book.setAuthor(author);

        bookRepository.saveAndFlush(book);

        Long bookId = book.getId();
        Book updatedBook = bookRepository.findById(bookId).orElse(null);
        assertNotNull(updatedBook);

        updatedBook.setName("The Ravenclow");
        bookRepository.saveAndFlush(updatedBook);

        Book fetchedUpdatedBook = bookRepository.findById(bookId).orElse(null);
        assertNotNull(fetchedUpdatedBook);

        assertEquals("The Ravenclow", fetchedUpdatedBook.getName());
    }

    @Test
    void testFindByGenreId() {
        Genre genre = new Genre();
        genre.setType("Horror");

        genreRepository.save(genre);

        Author author = new Author();
        author.setFullName("Allan Poe");
        author.setYearOfBirth(1809);

        authorRepository.save(author);

        Book book = new Book();
        book.setName("The Raven");
        book.setYear(1845);
        book.setAuthor(author);

        bookRepository.save(book);

        List<Book> books = bookRepository.findByGenreId(genre.getId());
        assertEquals(0, books.size());
    }
}
