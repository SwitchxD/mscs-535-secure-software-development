package com.projects.assignments.controller;
import com.projects.assignments.service.ServiceFileReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileReaderController {

    private final ServiceFileReader fileReaderService;

    public FileReaderController(ServiceFileReader fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    /**
     * POST /api/files/read
     * Accepts a multipart text file and returns its lines.
     * The file is never stored on disk or in the database.
     */
    @PostMapping("/read")
    public ResponseEntity<?> readFile(@RequestParam("file") MultipartFile file) {
        List<String> lines = fileReaderService.readFile(file);

        if (lines.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Could not read file."));
        }

        return ResponseEntity.ok(Map.of("lineCount", lines.size(), "lines", lines));
    }
}
