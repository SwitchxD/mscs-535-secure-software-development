package com.projects.assignments.controller;

import com.projects.assignments.entity.Vehicle;
import com.projects.assignments.service.ServiceVehicle;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final ServiceVehicle vehicleService;

    public VehicleController(ServiceVehicle vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }

    @GetMapping
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping("/{id}/forward")
    public String forward(@PathVariable Long id) {
        return vehicleService.forward(id);
    }

    @GetMapping("/{id}/reverse")
    public String reverse(@PathVariable Long id) {
        return vehicleService.reverse(id);
    }
}