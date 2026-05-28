package com.projects.assignments.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ServiceFileReader {

    private static final Logger LOGGER = Logger.getLogger(ServiceFileReader.class.getName());

    /**
     * Reads all lines from an uploaded file without storing it.
     * Returns an empty list if anything goes wrong.
     */
    public List<String> readFile(MultipartFile file) {

        // Reject null or empty uploads before any processing
        if (file == null || file.isEmpty()) {
            LOGGER.warning("Uploaded file is null or empty.");
            return Collections.emptyList();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            List<String> lines = reader.lines().toList();
            LOGGER.info("Read " + lines.size() + " line(s) from: " + file.getOriginalFilename());
            return lines;

        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, "Permission denied reading file: " + file.getOriginalFilename(), e);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to read file: " + file.getOriginalFilename(), e);
        }

        return Collections.emptyList();
    }
}