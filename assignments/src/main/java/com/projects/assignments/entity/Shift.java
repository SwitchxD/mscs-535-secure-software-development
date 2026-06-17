package com.projects.assignments.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

// One record of an employee tied to a day and a shift.
// Used both for what an employee *wants* (a preference) and what they
// *get* (a final assignment) - same shape, different point in the flow.
// @Getter generates getEmployeeName()/getDayOfWeek()/getShiftType().
// @AllArgsConstructor generates the 3-argument constructor used elsewhere.
@Getter
@AllArgsConstructor
public class Shift {

    public enum ShiftType { MORNING, AFTERNOON, EVENING }

    private String employeeName;
    private DayOfWeek dayOfWeek;
    private ShiftType shiftType;
}