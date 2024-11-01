package com.example.schedulemanagement.Service;

import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.ScheduleRepository;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto addSchedule(String password, ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTask(), dto.getAuthorName(), password);

        return scheduleRepository.addSchedule(password, schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName) {
        return scheduleRepository.findAllSchedules(updatedAt, authorName);
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.getScheduleById(scheduleId);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId,
        String task, String authorName) {
        if (task == null || authorName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The task and authorName are required values");
        }
        int updatedRow = scheduleRepository.updateSchedule(scheduleId, task, authorName);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }

        Schedule schedule = scheduleRepository.getScheduleById(scheduleId);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        int deletedRow = scheduleRepository.deleteSchedule(scheduleId);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
    }
}
