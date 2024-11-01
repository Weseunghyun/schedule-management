package com.example.schedulemanagement.entity;

import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class Schedule {
    private Long id;
    private String task;
    private String authorName;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Schedule(String task, String authorName, String password) {
        this.task = task;
        this.authorName = authorName;
        this.password = password;
    }
}
