package org.example.service.impl;

import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.mapper.BookMapper;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = getBookById(id);
        return bookMapper.toDto(book);
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        Optional<Author> author = authorRepository.findById(bookCreateDto.getAuthorId());
        if (author.isEmpty()) {
            throw new EntityNotFoundException("Wrong author id, this author doesn't exist.");
        }
        Book book = bookMapper.toEntity(bookCreateDto);
        return bookMapper.toDto(bookRepository.saveAndFlush(book));
    }

    @Transactional
    @Override
    public BookDto update(Long id, BookCreateDto bookDto) {
        Book book = getBookById(id);
        if (bookDto.getAuthorId() != null) {
            Author author = getExistingAuthorById(bookDto.getAuthorId());
            book.setAuthor(author);
        }
        Book updatedBook = bookMapper.partialUpdateToEntity(bookDto, book);

        return bookMapper.toDto(bookRepository.saveAndFlush(updatedBook));
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("This book doesn't exist.");
        } else {
            bookRepository.deleteById(id);
            return true;
        }
    }

    @Transactional
    @Override
    public void setBookGenre(Long bookId, Long genreId) {
        Book book = getBookById(bookId);
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("This book doesn't exist."));
        book.getGenres().add(genre);
        bookRepository.saveAndFlush(book);
    }

    @Override
    public List<BookDto> findByGenreId(Long id) {
        List<Book> books = bookRepository.findByGenreId(id);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private Author getExistingAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + authorId + " not found."));
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This book doesn't exist."));
    }
}
