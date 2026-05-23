package com.projects.assignments.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("Hybrid")
public class Hybrid extends Vehicle {

    public Hybrid(String name) {
        super(name);
    }

    @Override
    public String forward() {
        return getName() + " (Hybrid) is moving forward";
    }

    @Override
    public String reverse() {
        return getName() + " (Hybrid) is reversing";
    }
}