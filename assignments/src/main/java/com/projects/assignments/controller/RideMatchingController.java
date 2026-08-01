package com.projects.assignments.controller;

import com.projects.assignments.service.RideMatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride-matching")
public class RideMatchingController {

    private static final Logger log = LoggerFactory.getLogger(RideMatchingController.class);

    @Autowired
    private RideMatchingService rideMatchingService;

    @PostMapping("/run")
    public ResponseEntity<String> runProcessing(
            @RequestParam(defaultValue = "10") int rideRequestCount,
            @RequestParam(defaultValue = "4") int driverCount) {
        try {
            rideMatchingService.runProcessing(rideRequestCount, driverCount);
            return ResponseEntity.ok("Processed " + rideRequestCount + " ride requests with " + driverCount + " drivers");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Ride matching run was interrupted", e);
            return ResponseEntity.internalServerError().body("Ride matching interrupted");
        } catch (Exception e) {
            log.error("Ride matching run failed", e);
            return ResponseEntity.internalServerError().body("Ride matching failed: " + e.getMessage());
        }
    }
}
