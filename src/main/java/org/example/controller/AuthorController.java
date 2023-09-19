package org.example.controller;

import org.example.dto.AuthorDto;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


    }
