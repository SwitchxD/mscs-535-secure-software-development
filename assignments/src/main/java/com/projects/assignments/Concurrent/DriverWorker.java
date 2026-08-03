package com.projects.assignments.Concurrent;

import com.projects.assignments.entity.RideRequest;
import com.projects.assignments.entity.RideResult;
import com.projects.assignments.repository.RideResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import java.util.concurrent.CountDownLatch;

/**
 * A single concurrent unit of execution that repeatedly pulls ride requests
 * from the shared {@link RideRequestQueue}, processes each one, and persists
 * the outcome through {@link RideResultRepository}.
 *
 * The worker terminates once getTask() returns null, signaling the shared
 * queue has been fully drained. All exceptions are caught locally so that
 * one failure never brings down the surrounding thread pool.
 */
public class DriverWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DriverWorker.class);

    private final int driverId;
    private final RideRequestQueue rideRequestQueue;
    private final CountDownLatch latch;
    private final RideResultRepository repository;

    public DriverWorker(int driverId, RideRequestQueue rideRequestQueue,
                        CountDownLatch latch, RideResultRepository repository) {
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
            // Restore the interrupt flag rather than swallowing it, per
            // standard Java concurrency convention.
            Thread.currentThread().interrupt();
            log.error("Driver-{} interrupted while waiting for work", driverId, e);
        } catch (Exception e) {
            log.error("Driver-{} encountered an unexpected error", driverId, e);
        } finally {
            log.info("Driver-{} completed", driverId);
            latch.countDown(); // always release the latch, even on failure
        }
    }

    /**
     * Simulates ride processing and persists the outcome. Persistence
     * failures are caught separately from interruption and from other
     * unexpected errors so each failure mode is logged distinctly and the
     * worker can continue to the next request rather than terminating.
     */
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
        } catch (DataAccessException e) {
            log.error("Driver-{} failed to persist result for ride {}", driverId, request.getId(), e);
        } catch (Exception e) {
            log.error("Driver-{} failed to process ride {}", driverId, request.getId(), e);
        }
    }
}