package org.example.parallelstrategy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ExampleSimpleThreadVirtual {

    static void main() throws InterruptedException {
        //Thread vThread = Thread.startVirtualThread(() -> IO.println("virtual threads"));
        //Thread vThread = Thread.ofVirtual().start(() -> IO.println("virtual threads"));
        Thread unstartedThread = Thread.ofVirtual().unstarted(() -> IO.println("virtual threads"));

        //vThread.join();
        unstartedThread.start();

        try(var virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            var future = virtualExecutor.submit(ExampleSimpleThreadVirtual::call);
            future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    static void call() {
        IO.println("ola");
    }
}
