package org.example.parallelstrategy;

import org.example.utility.ExecutionTimer;

public class Simulation {

    static void main() throws Exception {
        final CreditCalculateMain service = new CreditCalculateMain();
        System.out.println("=== Sequencial Execution ===");
        ExecutionTimer.measure(() -> service.calculteCredit(1L));

        System.out.println("=== Parallel Execution ===");
        ExecutionTimer.measure(() -> {
            try {
                return service.calculeCreditWithUnboundedThreads(1L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException();
            }
        });
    }
}
