package org.example.parallelstrategy;

public class SimpleThreadPoolDemo {

    static void main() throws InterruptedException {
        try(var threadPool = new SimpleThreadPool(2, 2)) {
            for (int i = 0; i < 100; i++) {
                int finalI = i;
                threadPool.submit(() -> runTask(finalI));
            }
        }

        Thread.sleep(10_000);
        IO.println("main thread finished");
    }

    private static void runTask(int id) {
        IO.println("task " + id + " on " + Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
