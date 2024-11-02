package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.Service.schedule.ScheduleService;
import com.example.schedulemanagement.dto.schedule.ScheduleRequestDto;
import com.example.schedulemanagement.dto.schedule.ScheduleResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> addSchedule(
        @RequestHeader("Authorization") String password,
        @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.addSchedule(password, dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
        @RequestParam(required = false) String updatedAt,
        @RequestParam(required = false) String authorName
    ) {
        return new ResponseEntity<>(scheduleService.findAllSchedules(updatedAt, authorName),
            HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long scheduleId) {
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
        @PathVariable Long scheduleId,
        @RequestBody ScheduleRequestDto dto,
        @RequestHeader("Authorization") String password
    ) {
        if (dto.getTask() == null || dto.getAuthorName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The task and authorName are required values");
        }
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto.getTask(), dto.getAuthorName(), password), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
        @PathVariable Long scheduleId,
        @RequestHeader("Authorization") String password
    ) {
        scheduleService.deleteSchedule(scheduleId, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
