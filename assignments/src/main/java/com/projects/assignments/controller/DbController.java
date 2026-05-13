package com.projects.assignments.controller;
import com.projects.assignments.service.ServiceSecureDbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/secure")
public class DbController {

    @Autowired
    private ServiceSecureDbConnection serviceSecureDbConnection;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {

        Map<String, Object> user = serviceSecureDbConnection.authenticateUser(username, password);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        Map<String, Object> user = serviceSecureDbConnection.getUserById(id);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(user);
    }
}
