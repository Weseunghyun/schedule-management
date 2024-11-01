package com.example.schedulemanagement.Service;

import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;

public interface ScheduleService {
    ScheduleResponseDto addSchedule(String password, ScheduleRequestDto scheduleRequestDto);
}
