package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SimulationSemphore {


    void main() throws InterruptedException {
        var semaphore = new Semaphore(5);

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++){
                int id = i;
                executor.submit(() -> {
                    try {
                        if (!semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
                            IO.println("rejeitado " + id);
                            return;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        IO.println("executando " + id + " em " + Thread.currentThread());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release();
                    }
                });
            }
        }

        IO.println("permits finais: " + semaphore.availablePermits());
    }


}
