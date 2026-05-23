package com.projects.assignments.service;

import com.projects.assignments.entity.Vehicle;
import com.projects.assignments.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceVehicle {

    private final VehicleRepository vehicleRepository;

    public ServiceVehicle(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public String forward(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return vehicle.forward();
    }

    public String reverse(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return vehicle.reverse();
    }
}
