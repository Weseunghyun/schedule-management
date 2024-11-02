package com.example.schedulemanagement.Service.Author;

import com.example.schedulemanagement.dto.author.AuthorRequestDto;
import com.example.schedulemanagement.dto.author.AuthorResponseDto;

public interface AuthorService {
    AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto);
}
