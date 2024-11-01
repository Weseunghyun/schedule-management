package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto addSchedule(String password, Schedule schedule) {
        // 데이터 삽입
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(
                "schedules").usingGeneratedKeyColumns("schedules_id")
            .usingColumns("task", "author_name", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("author_name", schedule.getAuthorName());
        parameters.put("password", password);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        long scheduleId = key.longValue();

        // 삽입된 데이터를 다시 조회하여 created_at과 updated_at 가져오기
        String query = "SELECT schedules_id, task, author_name, created_at, updated_at FROM Schedules WHERE schedules_id = ?";
        List<ScheduleResponseDto> scheduleResponseDtos = jdbcTemplate.query(query,
            scheduleRowMapper(), scheduleId);

        // 조회 결과가 존재할 경우 첫 번째 ScheduleResponseDto 반환
        if (!scheduleResponseDtos.isEmpty()) {
            return scheduleResponseDtos.get(0);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "schedule not found");
        }

    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName) {
        //동적 쿼리를 구현해야함.
        StringBuilder sql = new StringBuilder("SELECT * FROM schedules");
        List<String> args = new ArrayList<>();

        if (updatedAt != null) {
            sql.append(" WHERE DATE(updated_at) = ?");
            args.add(updatedAt);
        }

        if (authorName != null) {
            if (updatedAt != null) {
                sql.append(" AND");
            } else {
                sql.append(" WHERE");
            }
            sql.append(" author_name = ?");
            args.add(authorName);
        }

        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), args.toArray());
    }

    @Override
    public Schedule findScheduleById(Long scheduleId) {
        List<Schedule> schedules = jdbcTemplate.query(
            "SELECT * FROM schedules WHERE schedules_id = ?", scheduleRowMapperV2(), scheduleId);
        return schedules.stream().findAny()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public int updateSchedule(Long scheduleId, String task, String authorName, String password) {
        if (validatePassword(scheduleId, password))  {
            return jdbcTemplate.update("UPDATE schedules SET task = ?, author_name = ? where schedules_id = ?", task,
                authorName, scheduleId);
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password does not match");
        }
    }

    @Override
    public int deleteSchedule(Long scheduleId, String password) {
        if (validatePassword(scheduleId, password))  {
            return jdbcTemplate.update("DELETE FROM schedules WHERE schedules_id = ?", scheduleId);
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password does not match");
        }
    }


    // RowMapper 메서드로 분리
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(rs.getLong("schedules_id"),
            rs.getString("task"), rs.getString("author_name"), rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at"));
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (rs, rowNum) -> new Schedule(rs.getLong("schedules_id"), rs.getString("task"),
            rs.getString("author_name"), rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at"));
    }

    private RowMapper<Schedule> scheduleRowMapperV3() {
        return (rs, rowNum) -> new Schedule(rs.getLong("schedules_id"), rs.getString("task"),
            rs.getString("author_name"), rs.getString("password"), rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at"));
    }

    private boolean validatePassword(Long scheduleId, String password) {
        List<Schedule> scheduleList = jdbcTemplate.query(
            "SELECT * FROM schedules WHERE schedules_id = ?",
            scheduleRowMapperV3(), scheduleId);

        if (!scheduleList.isEmpty()) {
            Schedule schedule = scheduleList.get(0);
            if (schedule.getPassword().equals(password)) {
                return true;
            }else {
                return false;
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "schedule not found");
        }
    }
}

