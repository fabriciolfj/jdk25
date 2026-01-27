package org.example.parallelstrategy;

import java.time.Duration;
import java.util.stream.IntStream;

public class UsoThreadVirtualComThreaLocal {

    static void main() {
        ThreadLocal<LargeObject> threadLocal = ThreadLocal.withInitial(LargeObject::new);
        var threadList= IntStream.range(0, 2000)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                    LargeObject largeObject = threadLocal.get();
                    useIt(largeObject);
                    sleep();
                })).toList();

        threadList.forEach(Thread::start);
        threadList.forEach(thread -> {
           try {
               thread.join();
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
           }
        });
    }

    private static void sleep() {
        try {
            Thread.sleep(Duration.ofMinutes(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void useIt(final LargeObject largeObject) {
        IO.println(largeObject.data.length);
    }

    static class LargeObject {
        private byte[] data = new byte[1024 * 400];
    }
}


