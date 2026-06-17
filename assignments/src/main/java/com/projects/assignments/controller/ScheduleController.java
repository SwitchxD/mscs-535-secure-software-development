package com.projects.assignments.controller;

import com.projects.assignments.entity.Shift;
import com.projects.assignments.repository.ScheduleRepository;
import com.projects.assignments.service.ScheduleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.List;

@RestController
public class ScheduleController {

    private final ScheduleRepository repository;
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleRepository repository, ScheduleService scheduleService) {
        this.repository = repository;
        this.scheduleService = scheduleService;
    }

    // Add a new employee. Example: POST /employees?name=Alice
    @PostMapping("/employees")
    public List<String> addEmployee(@RequestParam String name) {
        repository.addEmployee(name);
        return repository.getEmployeeNames();
    }

    // Add a shift preference for an employee.
    // Example: POST /preferences?name=Alice&dayOfWeek=MONDAY&shiftType=MORNING
    @PostMapping("/preferences")
    public List<Shift> addPreference(@RequestParam String name,
                                     @RequestParam DayOfWeek dayOfWeek,
                                     @RequestParam Shift.ShiftType shiftType) {
        repository.addPreference(new Shift(name, dayOfWeek, shiftType));
        return repository.getPreferences();
    }

    // Run the scheduling algorithm and return the result.
    // Example: POST /schedule
    @PostMapping("/schedule")
    public List<Shift> generateSchedule() {
        return scheduleService.generateSchedule();
    }
}
