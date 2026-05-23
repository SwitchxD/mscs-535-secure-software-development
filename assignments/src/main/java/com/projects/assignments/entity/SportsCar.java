package com.projects.assignments.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("SportsCar")
public class SportsCar extends Vehicle {

    public SportsCar(String name) {
        super(name);
    }

    @Override
    public String forward() {
        return getName() + " (SportsCar) is accelerating forward";
    }

    @Override
    public String reverse() {
        return getName() + " (SportsCar) is reversing";
    }
}

