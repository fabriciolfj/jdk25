package org.example.parallelstrategy;

import java.util.concurrent.ForkJoinPool;

public class ExampleForkjoinpool {

    static void main() {
        try(ForkJoinPool forkJoinPool = new ForkJoinPool(4,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true)) {
            for (int i = 0; i < 10; i++) {
                forkJoinPool.submit(new EventTask("Event " + 1));
            }
        }
    }

    record EventTask(String name) implements Runnable {

        @Override
        public void run() {
            System.out.println("Processing " + name + " in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
