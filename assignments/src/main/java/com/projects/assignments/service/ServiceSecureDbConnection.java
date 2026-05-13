package com.projects.assignments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServiceSecureDbConnection {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Authenticates user securely using parameterized queries.
     */
    public Map<String, Object> authenticateUser(String username, String password) {
        String sql = "SELECT id, username, email FROM users WHERE username = ? AND password = ?";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, username, password);

        if (results.isEmpty()) {
            return null;
        }
        return results.getFirst();
    }

    /**
     * Looks up a user by ID using a parameterized query.
     */
    public Map<String, Object> getUserById(Long id) {
        String sql = "SELECT id, username, email FROM users WHERE id = ?";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, id);

        if (results.isEmpty()) {
            return null;
        }
        return results.getFirst();
    }
}
