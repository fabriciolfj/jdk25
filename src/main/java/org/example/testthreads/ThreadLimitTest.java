package org.example.testthreads;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadLimitTest {

    static void main() {
        var threadCount = new AtomicInteger(0);
        try {
            while(true) {
                var thread = new Thread(() -> {
                    threadCount.incrementAndGet();
                    LockSupport.park();
                });

                thread.start();
            }

        } catch (OutOfMemoryError err) {
            IO.println("reached thread limit " + threadCount );
            err.printStackTrace();
        }
    }
}
