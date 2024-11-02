package com.example.schedulemanagement.repository.Author;


import com.example.schedulemanagement.dto.author.AuthorResponseDto;
import com.example.schedulemanagement.entity.Author;

public interface AuthorRepository {
    AuthorResponseDto addAuthor(Author author);
}
