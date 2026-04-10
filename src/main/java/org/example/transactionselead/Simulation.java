package org.example.transactionselead;

import java.time.Instant;
import java.util.UUID;

public class Simulation {


    static void main() {
        final var value = get();

        switch (value) {
            case TransactionResult.Success(String txId, var time) -> {
                IO.println("success");
            }
            case TransactionResult.InsufficientFunds(var reg, var avail) -> {
                IO.println("Insufficient funds");
            }
        }
    }

    private static TransactionResult get() {
        return new TransactionResult.Success(UUID.randomUUID().toString(), Instant.now());
    }
}
