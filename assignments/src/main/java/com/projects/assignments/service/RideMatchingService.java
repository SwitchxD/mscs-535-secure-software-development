package com.projects.assignments.service;

import com.projects.assignments.Concurrent.DriverWorker;
import com.projects.assignments.Concurrent.RideRequestQueue;
import com.projects.assignments.entity.RideRequest;
import com.projects.assignments.repository.RideResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.*;

@Service
public class RideMatchingService {

    @Autowired
    private RideResultRepository repository;

    public void runProcessing(int rideRequestCount, int driverCount) throws InterruptedException {
        RideRequestQueue rideRequestQueue = new RideRequestQueue();
        for (int i = 1; i <= rideRequestCount; i++) {
            rideRequestQueue.addTask(new RideRequest(i, "Zone-" + i));
        }

        CountDownLatch latch = new CountDownLatch(driverCount);
        ExecutorService executor = Executors.newFixedThreadPool(driverCount);

        for (int i = 1; i <= driverCount; i++) {
            executor.submit(new DriverWorker(i, rideRequestQueue, latch, repository));
        }

        latch.await(); // blocks until all drivers finish (queue drained, getTask() returns null)
        executor.shutdown();
    }
}
