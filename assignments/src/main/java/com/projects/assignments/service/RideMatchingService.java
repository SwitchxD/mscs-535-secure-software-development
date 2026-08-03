package com.projects.assignments.service;

import com.projects.assignments.Concurrent.DriverWorker;
import com.projects.assignments.Concurrent.RideRequestQueue;
import com.projects.assignments.entity.RideRequest;
import com.projects.assignments.repository.RideResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

/**
 * Orchestrates a concurrent ride-matching run: preloads the shared queue,
 * launches one DriverWorker per driver on a fixed thread pool, and blocks
 * until every worker has finished before releasing pool resources.
 */
@Service
public class RideMatchingService {
    private static final Logger log = LoggerFactory.getLogger(RideMatchingService.class);

    @Autowired
    private RideResultRepository repository;

    public void runProcessing(int rideRequestCount, int driverCount) throws InterruptedException {
        RideRequestQueue rideRequestQueue = new RideRequestQueue();
        for (int i = 1; i <= rideRequestCount; i++) {
            rideRequestQueue.addTask(new RideRequest(i, "Zone-" + i));
        }

        CountDownLatch latch = new CountDownLatch(driverCount);
        ExecutorService executor = Executors.newFixedThreadPool(driverCount);

        try {
            for (int i = 1; i <= driverCount; i++) {
                executor.submit(new DriverWorker(i, rideRequestQueue, latch, repository));
            }
            latch.await(); // block until every driver has drained the queue
        } finally {
            shutdownExecutor(executor);
        }
    }

    /**
     * Shuts the pool down gracefully, falling back to a forced shutdown if
     * threads do not terminate within the grace period, guaranteeing no
     * pool threads are leaked past the end of a run.
     */
    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                log.warn("Executor did not terminate in time; forcing shutdown");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
