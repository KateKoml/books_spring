package org.example.controller;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.service.BookService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        List<BookDto> bookDtos = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookCreateDto bookCreateDto) {
        BookDto createdBook = bookService.create(bookCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id,
                                              @RequestBody BookCreateDto bookDto) {
        BookDto book = bookService.update(id, bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        boolean result = bookService.delete(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Book successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book wasn't found");
        }
    }

    @PutMapping("/{bookId}/genres/{genreId}")
    public ResponseEntity<String> addBookGenre(@PathVariable Long bookId, @PathVariable Long genreId) {
        bookService.setBookGenre(bookId, genreId);
        return ResponseEntity.status(HttpStatus.OK).body("Now book with id: " + bookId + " has genre with id: " + genreId);
    }

    @GetMapping("/genres/{genreId}")
    public ResponseEntity< List<BookDto>> findBookByGenreId(@PathVariable Long genreId) {
        List<BookDto> bookDtos = bookService.findByGenreId(genreId);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }
}
