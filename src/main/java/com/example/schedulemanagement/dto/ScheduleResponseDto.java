package com.example.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String tasks;
    private String authorName;
    private String createdAt;
    private String updatedAt;
}
