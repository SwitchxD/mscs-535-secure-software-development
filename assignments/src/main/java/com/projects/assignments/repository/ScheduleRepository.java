package com.projects.assignments.repository;

import com.projects.assignments.entity.Shift;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// Simple in-memory storage. No database - everything lives in lists
// for as long as the application is running.
@Getter
@Repository
public class ScheduleRepository {

    private final List<String> employeeNames = new ArrayList<>();
    private final List<Shift> preferences = new ArrayList<>();
    private final List<Shift> assignments = new ArrayList<>();

    public void addEmployee(String name) {
        employeeNames.add(name);
    }

    public void addPreference(Shift preference) {
        preferences.add(preference);
    }

    public void saveAssignments(List<Shift> newAssignments) {
        assignments.clear();
        assignments.addAll(newAssignments);
    }

}
