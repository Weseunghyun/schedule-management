package com.example.schedulemanagement.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String authorName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
