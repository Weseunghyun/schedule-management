package com.example.schedulemanagement.Service.schedule;

import com.example.schedulemanagement.dto.schedule.ScheduleRequestDto;
import com.example.schedulemanagement.dto.schedule.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.schedule.ScheduleRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
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
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId,
        String task, String authorName, String password) {

        int updatedRow = scheduleRepository.updateSchedule(scheduleId, task, authorName, password);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }

        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId, String password) {
        int deletedRow = scheduleRepository.deleteSchedule(scheduleId, password);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
    }
}
