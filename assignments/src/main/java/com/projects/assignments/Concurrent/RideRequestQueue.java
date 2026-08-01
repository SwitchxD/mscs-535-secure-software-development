package com.projects.assignments.Concurrent;

import com.projects.assignments.entity.RideRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RideRequestQueue {
    private final BlockingQueue<RideRequest> queue = new LinkedBlockingQueue<>();

    public void addTask(RideRequest request) {
        queue.offer(request);
    }

    public RideRequest getTask() throws InterruptedException {
        return queue.poll(500, TimeUnit.MILLISECONDS);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
