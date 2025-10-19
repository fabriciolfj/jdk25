package org.example.stablevalue;

import java.util.List;

public class GuitarStore {

    static final int POOL_SIZE = 10;
    static final List<OrderController> ORDERS = StableValue.list(POOL_SIZE, _ -> new OrderController());

    public static OrderController orders() {
        long index = Thread.currentThread().threadId() % POOL_SIZE;
        return ORDERS.get((int) index);
    }
}
