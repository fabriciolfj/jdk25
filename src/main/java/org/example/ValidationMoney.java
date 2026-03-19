package org.example;

import java.math.BigDecimal;

public interface ValidationMoney {

    boolean isValid();

    default boolean and(ValidationMoney other) {
        return this.isValid() && other.isValid();
    }
}
