package com.projects.assignments.service;


import com.projects.assignments.entity.Shift;
import com.projects.assignments.entity.Shift.ShiftType;
import com.projects.assignments.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ScheduleService {

    // Rules from the assignment.
    private static final int MAX_DAYS_PER_WEEK = 5; // an employee works at most 5 days
    private static final int MIN_PER_SHIFT = 2;      // each shift needs at least 2 people
    private static final int MAX_PER_SHIFT = 3;      // a shift is "full" once it hits this many

    private final ScheduleRepository repository;
    private final Random random = new Random();

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    // Builds the weekly schedule from stored employees and preferences.
    public List<Shift> generateSchedule() {
        List<String> employees = repository.getEmployeeNames();
        List<Shift> schedule = new ArrayList<>(); // the roster we are building, slot by slot

        // STEP 1: try to honor each preference.
        for (Shift pref : repository.getPreferences()) {
            String name = pref.getEmployeeName();
            DayOfWeek day = pref.getDayOfWeek();
            Shift.ShiftType wanted = pref.getShiftType();

            // Skip if they already hit 5 days or already work this day.
            if (daysWorked(schedule, name) >= MAX_DAYS_PER_WEEK) {
                continue;
            }
            if (isWorkingOn(schedule, name, day)) {
                continue;
            }

            // Try the preferred shift; if it's full, resolve the conflict.
            if (!tryAssign(schedule, name, day, wanted)) {
                resolveConflict(schedule, name, day, wanted);
            }
        }

        // STEP 2: make sure every shift has at least 2 people.
        // If not, randomly add employees who still have room.
        for (DayOfWeek day : DayOfWeek.values()) {
            for (ShiftType shift : ShiftType.values()) {
                while (countInShift(schedule, day, shift) < MIN_PER_SHIFT) {
                    String filler = pickRandomFreeEmployee(schedule, employees, day);
                    if (filler == null) {
                        break; // nobody left who can work this day
                    }
                    tryAssign(schedule, filler, day, shift);
                }
            }
        }

        repository.saveAssignments(schedule);
        return schedule;
    }

    // Adds the employee to this day/shift if there is room. Returns false if not.
    private boolean tryAssign(List<Shift> schedule, String name, DayOfWeek day, ShiftType shift) {
        if (isWorkingOn(schedule, name, day)) {
            return false;
        }
        if (daysWorked(schedule, name) >= MAX_DAYS_PER_WEEK) {
            return false;
        }
        if (countInShift(schedule, day, shift) >= MAX_PER_SHIFT) {
            return false; // shift is full
        }

        schedule.add(new Shift(name, day, shift));
        return true;
    }

    // Preferred shift was full: try the other shifts today, then tomorrow.
    private void resolveConflict(List<Shift> schedule, String name, DayOfWeek day, ShiftType wanted) {
        // Try the other shifts on the same day first.
        for (ShiftType other : ShiftType.values()) {
            if (other != wanted && tryAssign(schedule, name, day, other)) {
                return;
            }
        }

        // If today is full everywhere, try any shift on the next day.
        DayOfWeek nextDay = day.plus(1);
        for (ShiftType shift : ShiftType.values()) {
            if (tryAssign(schedule, name, nextDay, shift)) {
                return;
            }
        }
    }

    // Picks a random employee who is free on this day and under 5 days worked.
    private String pickRandomFreeEmployee(List<Shift> schedule, List<String> employees, DayOfWeek day) {
        List<String> available = new ArrayList<>();
        for (String name : employees) {
            if (!isWorkingOn(schedule, name, day) && daysWorked(schedule, name) < MAX_DAYS_PER_WEEK) {
                available.add(name);
            }
        }

        if (available.isEmpty()) {
            return null;
        }
        return available.get(random.nextInt(available.size()));
    }

    // How many days this employee is already scheduled for.
    private int daysWorked(List<Shift> schedule, String name) {
        int count = 0;
        for (Shift s : schedule) {
            if (s.getEmployeeName().equals(name)) {
                count++;
            }
        }
        return count;
    }

    // Whether this employee already has a shift on this day.
    private boolean isWorkingOn(List<Shift> schedule, String name, DayOfWeek day) {
        for (Shift s : schedule) {
            if (s.getEmployeeName().equals(name) && s.getDayOfWeek() == day) {
                return true;
            }
        }
        return false;
    }

    // How many employees are currently in this day/shift slot.
    private int countInShift(List<Shift> schedule, DayOfWeek day, ShiftType shift) {
        int count = 0;
        for (Shift s : schedule) {
            if (s.getDayOfWeek() == day && s.getShiftType() == shift) {
                count++;
            }
        }
        return count;
    }
}
