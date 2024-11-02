package com.example.schedulemanagement.dto.author;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {

    private Long authorId;
    private String authorName;
    private String authorEmail;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
