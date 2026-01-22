package org.example.parallelstrategy;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ExampleAppV1 {

    static void main() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return 1;
                });
            });
        }
    }
}
