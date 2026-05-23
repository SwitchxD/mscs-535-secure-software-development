package com.projects.assignments.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("SUV")
public class Suv extends Vehicle {

    public Suv(String name) {
        super(name);
    }

    @Override
    public String forward() {
        return getName() + " (SUV) is driving forward";
    }

    @Override
    public String reverse() {
        return getName() + " (SUV) is reversing";
    }
}