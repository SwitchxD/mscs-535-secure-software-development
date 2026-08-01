package com.projects.assignments.Concurrent;

import com.projects.assignments.entity.RideRequest;
import com.projects.assignments.entity.RideResult;
import com.projects.assignments.repository.RideResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CountDownLatch;

public class DriverWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DriverWorker.class);

    private final int driverId;
    private final RideRequestQueue rideRequestQueue;
    private final CountDownLatch latch;
    private final RideResultRepository repository;

    public DriverWorker(int driverId, RideRequestQueue rideRequestQueue, CountDownLatch latch, RideResultRepository repository) {
        this.driverId = driverId;
        this.rideRequestQueue = rideRequestQueue;
        this.latch = latch;
        this.repository = repository;
    }

    @Override
    public void run() {
        log.info("Driver-{} started", driverId);
        try {
            RideRequest request;
            while ((request = rideRequestQueue.getTask()) != null) {
                processRide(request);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Driver-{} interrupted", driverId, e);
        } catch (Exception e) {
            log.error("Driver-{} encountered an error", driverId, e);
        } finally {
            log.info("Driver-{} completed", driverId);
            latch.countDown();
        }
    }

    private void processRide(RideRequest request) {
        try {
            Thread.sleep(200); // simulate ride matching / trip processing time

            RideResult result = new RideResult();
            result.setRideRequestId(request.getId());
            result.setOutcome("Completed ride from " + request.getPickupLocation());
            repository.save(result);

            log.info("Driver-{} completed ride request {}", driverId, request.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Driver-{} interrupted during ride {}", driverId, request.getId(), e);
        } catch (Exception e) {
            log.error("Driver-{} failed to save result for ride {}", driverId, request.getId(), e);
        }
    }
}