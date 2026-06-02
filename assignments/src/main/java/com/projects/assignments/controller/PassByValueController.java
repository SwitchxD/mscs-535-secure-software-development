package com.projects.assignments.controller;

import com.projects.assignments.service.ServicePassByValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passby")
public class PassByValueController {

    private final ServicePassByValue service;

    public PassByValueController(ServicePassByValue service) {
        this.service = service;
    }

    @GetMapping("/primitive")
    public String testPrimitive(@RequestParam int value) {
        return service.demoPrimitive(value);
    }

    @PostMapping("/object")
    public String testObjectMutation(@RequestBody int[] arr) {
        return service.demoObjectMutation(arr);
    }

    @GetMapping("/reference")
    public String testReferenceReassignment(@RequestParam String text) {
        return service.demoReferenceReassignment(text);
    }
}