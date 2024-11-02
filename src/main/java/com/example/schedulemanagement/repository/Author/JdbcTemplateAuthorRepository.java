package com.example.schedulemanagement.repository.Author;

import com.example.schedulemanagement.dto.author.AuthorResponseDto;
import com.example.schedulemanagement.entity.Author;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {

    JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AuthorResponseDto addAuthor(Author author) {
        // 데이터 삽입
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(
                "author").usingGeneratedKeyColumns("author_id")
            .usingColumns("name", "email");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", author.getAuthorName());
        parameters.put("email", author.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        long authorId = key.longValue();

        // 삽입된 데이터를 다시 조회하여 created_at과 updated_at 가져오기
        String query = "SELECT * FROM author WHERE author_id = ?";
        List<AuthorResponseDto> authorResponseDtos = jdbcTemplate.query(query,
            authorRowMapper(), authorId);

        // 조회 결과가 존재할 경우 첫 번째 authorResponseDto 반환
        if (!authorResponseDtos.isEmpty()) {
            return authorResponseDtos.get(0);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "schedule not found");
        }
    }

    private RowMapper<AuthorResponseDto> authorRowMapper() {
        return (rs, rowNum) -> new AuthorResponseDto(rs.getLong("author_id"),
            rs.getString("name"), rs.getString("email"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at"));

    }
}
