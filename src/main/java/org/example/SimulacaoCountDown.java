package org.example;

import java.util.concurrent.CountDownLatch;

public class SimulacaoCountDown {
    static void main() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        final Runnable runnable = () -> IO.println("ok");

        for (int i = 0; i < 3; i++) {
            Thread.ofVirtual().start(() -> {
                runnable.run();
                latch.countDown();
            });
        }

        latch.await();


    }


}
