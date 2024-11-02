package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.Service.Author.AuthorService;
import com.example.schedulemanagement.dto.author.AuthorRequestDto;
import com.example.schedulemanagement.dto.author.AuthorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/authors")
@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> addAuthor(
        @RequestBody AuthorRequestDto dto
    ){
        return new ResponseEntity<>(authorService.addAuthor(dto), HttpStatus.CREATED);
    }
}
