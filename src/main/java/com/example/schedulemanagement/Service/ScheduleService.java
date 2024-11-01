package com.example.schedulemanagement.Service;

import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto addSchedule(String password, ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName);

    ScheduleResponseDto getScheduleById(Long scheduleId);

    ScheduleResponseDto updateSchedule(Long scheduleId, String task, String authorName);

    void deleteSchedule(Long scheduleId);
}
