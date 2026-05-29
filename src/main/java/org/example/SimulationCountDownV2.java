package org.example;

import org.springframework.validation.annotation.Validated;

import java.util.concurrent.CountDownLatch;

public class SimulationCountDownV2 {

    static void main() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        var task = new Runnable() {

            @Override
            public void run() {
                IO.println("ok");
            }
        };

        for(int i = 0; i < 10; i++) {
            Thread.ofVirtual().start(() -> {
                task.run();

                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
    }
}
