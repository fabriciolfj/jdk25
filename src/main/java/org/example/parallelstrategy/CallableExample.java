package org.example.parallelstrategy;

import java.util.Map;
import java.util.concurrent.*;

public class CallableExample {

    static final Map<Integer, Long> cache = new ConcurrentHashMap<>(
            Map.of(0, 0L, 1, 1L)
    );

    static void main() throws InterruptedException {
        try (ExecutorService threadPool = Executors.newCachedThreadPool()) {
            Future<Long> fibonacciNumber = threadPool.submit(() -> fibonnaci(50));

            while (!fibonacciNumber.isDone()) { }

            System.out.println(fibonacciNumber.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Long fibonnaci(int i) {
        if (cache.containsKey(i)) {
            return cache.get(i);
        }

        long result = fibonnaci(i - 1) + fibonnaci(i - 2);
        cache.put(i, result);
        return result;
    }
}
