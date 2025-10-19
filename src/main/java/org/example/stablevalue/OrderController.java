package org.example.stablevalue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

class OrderController {
//    private final StableValue<Logger> logger = StableValue.of();
    private final Supplier<Logger> logger = StableValue.supplier(() -> LoggerFactory.getLogger(OrderController.class));
//    Logger getLogger() {
//        return logger.orElseSet(() -> LoggerFactory.getLogger(OrderController.class));
//    }

    void submitOrder(String user, List<String> guitar) {
        logger.get().info("Ordering new guitars...");

        // ...

        logger.get().info("New guitars have been ordered, let's get to work!");
    }
}