package com.example.schedulemanagement.entity;

import lombok.Getter;

@Getter
public class Schedule {
    private Long id;
    private String tasks;
    private String author_name;
    private String password;
    private String created_at;
    private String updated_at;
}
