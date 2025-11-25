package org.example.virtualthreads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Example1 {

    private static final Logger logger = Logger.getLogger(Example1.class.getName());
    private static final int NUMBER_OF_TASKS = 15;

    static void main() throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");
        Runnable taskr = () -> logger.info(Thread.currentThread().toString());

        Callable<Boolean> taskc = () -> {
            logger.info(Thread.currentThread().getName());
            return true;
        };

        Thread.sleep(1000);
        System.out.println("\nRunnable:");

        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS;  i++) {
                //executor.submit(taskc);
                executor.submit(taskr);
            }
        }

    }
}
