package org.example.service.impl;

import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.mapper.BookMapper;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
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
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This book doesn't exist."));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        Book book = bookMapper.toEntity(bookCreateDto);
        return bookMapper.toDto(bookRepository.saveAndFlush(book));
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book updatedBook = bookMapper.partialUpdateToEntity(bookDto, book);

        return bookMapper.toDto(bookRepository.saveAndFlush(updatedBook));
    }

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
}
