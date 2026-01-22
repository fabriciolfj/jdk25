package org.example.parallelstrategy;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class LittleExample {

    static void main() {
        int numTasks = 1000;
        int avResponseTimeMillis = 500;

        Runnable ioBoundTask = () -> {
            try {
                Thread.sleep(Duration.ofMillis(avResponseTimeMillis));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        IO.println("=== little law throughput comparison ===");
        IO.println("testing " + numTasks + " task with " + avResponseTimeMillis + " ms latency each");

        benchmark("Virtual threads", Executors.newVirtualThreadPerTaskExecutor(), ioBoundTask, numTasks);
        benchmark("Fixed pool 100", Executors.newFixedThreadPool(100), ioBoundTask, numTasks);
        benchmark("Fixed pool 500", Executors.newFixedThreadPool(500), ioBoundTask, numTasks);
        benchmark("Fixed pool 1000", Executors.newFixedThreadPool(1000), ioBoundTask, numTasks);
    }

    static void benchmark(final String type, final ExecutorService executorService, final Runnable task, final int numTasks) {
        final Instant start = Instant.now();
        final AtomicLong completedTasks = new AtomicLong();
        try(executorService) {
            IntStream.range(0, numTasks)
                    .forEach(i -> executorService.submit(() -> {
                        task.run();
                        completedTasks.incrementAndGet();
                    }));
        }

        final Instant end = Instant.now();
        final long duration = Duration.between(start, end).toMillis();
        double throughput = (double) completedTasks.get() / duration * 1000;
        IO.println(type + ", - Time: "  + duration + "ms," + " Throughput " + throughput);
    }
}
