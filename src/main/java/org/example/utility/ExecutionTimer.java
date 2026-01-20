package org.example.utility;

import java.util.concurrent.Callable;

public class ExecutionTimer {

    public static <T> T measure(Callable<T> task) throws Exception {
        long startTime = System.nanoTime();
        try {
            return task.call();
        } finally {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;
            IO.println("execution time " + duration + " milliseconds");
        }
    }
}
