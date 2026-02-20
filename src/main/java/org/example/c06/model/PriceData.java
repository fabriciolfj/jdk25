package org.example.c06.model;

import java.time.Instant;

public record PriceData(String exchange, String symbol, double price,
                        Instant timestamp) {
}
