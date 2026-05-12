package com.projects.assignments.controller;


import com.projects.assignments.dto.OneTimePadDto;
import com.projects.assignments.service.ServiceOneTimePad;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
public class getController {

    @Autowired
    ServiceOneTimePad serviceOneTimePad;

    @GetMapping(path = "/healthCheck")
    public String test (){
        String response = "Service is up and running";
        log.info("Test controller run success");
        return response;
    }

    @GetMapping(path = "/oneTimePad")
    public OneTimePadDto oneTimePad (){
        OneTimePadDto response = serviceOneTimePad.encrypt();
        log.info("Test controller run success");
        return response;
    }
}
