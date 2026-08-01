package com.projects.assignments.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class RideRequest {
    private int id;
    private String pickupLocation;
}
