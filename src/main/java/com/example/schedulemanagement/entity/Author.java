package com.example.schedulemanagement.entity;

import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class Author {
    private Long id;
    private String authorName;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Author(String authorName, String authorEmail) {
        this.authorName = authorName;
        this.email = authorEmail;
    }
}
