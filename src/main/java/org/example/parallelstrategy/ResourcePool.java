package org.example.parallelstrategy;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourcePool {

    private final Semaphore semaphore;
    private final AtomicInteger activeConnections;
    private final AtomicInteger peakConnections;

    public ResourcePool(int resourceCount) {
        this.semaphore = new Semaphore(resourceCount);
        this.activeConnections = new AtomicInteger(0);
        this.peakConnections = new AtomicInteger(0);
    }

    public Optional<String> useResource(final String query) {
        boolean acquire = false;
        try {
            acquire = semaphore.tryAcquire(5, TimeUnit.SECONDS);
            if (!acquire) {
                return Optional.empty();
            }
            int current = activeConnections.incrementAndGet();
            peakConnections.updateAndGet(peak -> Math.max(peak, current));
            return queryDataBase(query);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        } finally {
            if (acquire) {
                activeConnections.decrementAndGet();
                semaphore.release();
            }
        }
    }

    public int getActiveConnections() {
        return activeConnections.get();
    }

    public int getPeakConnections() {
        return peakConnections.get();
    }

    private Optional<String> queryDataBase(final String query) {

        try {
            Thread.sleep(new Random().nextInt(500) + 500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
        return Optional.of("Result for: " + query);
    }
}
