package com.projects.assignments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentsApplication {

    private static final Log log = LogFactory.getLog(AssignmentsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AssignmentsApplication.class, args);
    }

}
