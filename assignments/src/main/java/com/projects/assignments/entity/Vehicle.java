package com.projects.assignments.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "vehicle_type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Suv.class, name = "SUV"),
        @JsonSubTypes.Type(value = SportsCar.class, name = "SportsCar"),
        @JsonSubTypes.Type(value = Hybrid.class, name = "Hybrid")
})
@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "VEHICLE", schema = "PUBLIC")
@DiscriminatorColumn(name = "vehicle_type")
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

    public abstract String forward();
    public abstract String reverse();
}
