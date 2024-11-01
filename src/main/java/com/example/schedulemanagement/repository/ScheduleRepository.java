package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto addSchedule(String password, Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName);
}
