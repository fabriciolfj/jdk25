package org.example.parallelstrategy;

import org.ietf.jgss.Oid;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResoucePoolTest {

    static void main() throws Exception {
        int maxConcurretThreads = 5;
        int totalRequests = 50;
        var pool  = new ResourcePool(maxConcurretThreads);

        var futures = new ArrayList<Future<Optional<String>>>();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalRequests; i++){
                final int taskId = i;
                futures.add(executor.submit(() -> pool.useResource("query " + taskId)));

                int successCount = 0;
                int timeoutCount = 0;

                for (Future<Optional<String>> future: futures) {
                    Optional<String> result = future.get();
                    if (result.isPresent()) {
                        successCount++;
                    } else {
                        timeoutCount++;
                    }
                }

                System.out.printf("""
                requests: %d
                successful: %d
                time-out: %d
                peak usage: %d%n""",
                        totalRequests,
                        successCount,
                        timeoutCount,
                        pool.getPeakConnections());

                System.out.println("=============" +taskId);
            }
            assert pool.getPeakConnections() <= maxConcurretThreads : "peak connections exceeded limit!";
        }
    }
}
