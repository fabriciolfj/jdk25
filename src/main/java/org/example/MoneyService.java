package org.example;

import java.math.BigDecimal;

public class MoneyService implements ValidationMoney {
    private BigDecimal value;

    public MoneyService(final BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean isValid() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }

    public BigDecimal getValue() {
        return value;
    }
}
