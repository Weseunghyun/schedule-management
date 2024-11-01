package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto addSchedule(String password, Schedule schedule) {
        // 데이터 삽입
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("schedules")
            .usingGeneratedKeyColumns("schedules_id")
            .usingColumns("task", "author_name", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("author_name", schedule.getAuthorName());
        parameters.put("password", password);


        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        long scheduleId = key.longValue();

        // 삽입된 데이터를 다시 조회하여 created_at과 updated_at 가져오기
        String query = "SELECT schedules_id, task, author_name, created_at, updated_at FROM Schedules WHERE schedules_id = ?";
        List<ScheduleResponseDto> scheduleResponseDtos = jdbcTemplate.query(query, scheduleRowMapper(), scheduleId);

        // 조회 결과가 존재할 경우 첫 번째 ScheduleResponseDto 반환
        if (!scheduleResponseDtos.isEmpty()) {
            return scheduleResponseDtos.get(0);
        } else {
            throw new DataRetrievalFailureException("Failed to retrieve schedule with id = " + scheduleId);
        }

    }

    // RowMapper 메서드로 분리
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
            rs.getLong("schedules_id"),
            rs.getString("task"),
            rs.getString("author_name"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}

