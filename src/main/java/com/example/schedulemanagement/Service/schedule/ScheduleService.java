package com.example.schedulemanagement.Service.schedule;

import com.example.schedulemanagement.dto.schedule.ScheduleRequestDto;
import com.example.schedulemanagement.dto.schedule.ScheduleResponseDto;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto addSchedule(String password, ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName);

    ScheduleResponseDto findScheduleById(Long scheduleId);

    ScheduleResponseDto updateSchedule(Long scheduleId, String task, String authorName, String password);

    void deleteSchedule(Long scheduleId, String password);
}
