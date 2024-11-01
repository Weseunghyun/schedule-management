package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto addSchedule(String password, Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName);

    Schedule getScheduleById(Long scheduleId);

    int updateSchedule(Long scheduleId, String task, String authorName);

    int deleteSchedule(Long scheduleId);
}
