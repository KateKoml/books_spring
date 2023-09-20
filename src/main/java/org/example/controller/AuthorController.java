package org.example.controller;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.service.AuthorService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequestMapping("/authors")
    public class AuthorController {
        private final AuthorService authorService;

        @Autowired
        public AuthorController(AuthorService authorService) {
            this.authorService = authorService;
        }

        @GetMapping
        public ResponseEntity<List<AuthorDto>> findAll() {
            List<AuthorDto> authorDtos = authorService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(authorDtos);
        }

        @GetMapping("/{id}")
        public ResponseEntity<AuthorDto> findAuthorById(@PathVariable Long id) {
            AuthorDto author = authorService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        }

        @PostMapping
        public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
            AuthorDto createdAuthor = authorService.create(authorCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
        }
        @PutMapping("/{id}")
        public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id,
                                                      @RequestBody AuthorCreateDto authorDto) {
            AuthorDto author = authorService.update(id, authorDto);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        }

        @DeleteMapping("/{id}")
        public  ResponseEntity<String> deleteAuthorById(@PathVariable Long id) {
            boolean result = authorService.delete(id);
            if (result) {
                return ResponseEntity.status(HttpStatus.OK).body("Author successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author wasn't found");
            }
        }
    }
