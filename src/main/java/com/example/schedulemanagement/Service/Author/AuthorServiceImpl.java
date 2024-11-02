package com.example.schedulemanagement.Service.Author;

import com.example.schedulemanagement.dto.author.AuthorRequestDto;
import com.example.schedulemanagement.dto.author.AuthorResponseDto;
import com.example.schedulemanagement.entity.Author;
import com.example.schedulemanagement.repository.Author.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author(authorRequestDto.getAuthorName(), authorRequestDto.getAuthorEmail());
        return authorRepository.addAuthor(author);
    }
}
