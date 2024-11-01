package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;

public interface ScheduleRepository {
    ScheduleResponseDto addSchedule(String password, Schedule schedule);
}
