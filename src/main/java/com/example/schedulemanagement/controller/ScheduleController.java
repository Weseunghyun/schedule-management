package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.Service.ScheduleService;
import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.addSchedule(password,dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
        @RequestParam(required = false) String updatedAt,
        @RequestParam(required = false) String authorName
    ){
        return new ResponseEntity<>(scheduleService.findAllSchedules(updatedAt, authorName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id){
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }
}
