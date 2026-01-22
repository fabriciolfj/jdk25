package org.example.parallelstrategy;

import java.util.HashSet;
import java.util.Set;

public class ThreadVirtualGroup {

    static void main() throws InterruptedException {
        Set<ThreadGroup> threadGroups = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Thread vThread = Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threadGroups.add(vThread.getThreadGroup());
        }

        Thread.sleep(1000);
        IO.println("unique thread groups " + threadGroups.size());
        IO.println("unique thread groups " + threadGroups.iterator().next());
    }
}
