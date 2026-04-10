package org.example.transactionselead;

import java.math.BigDecimal;
import java.time.Instant;

public sealed interface TransactionResult permits TransactionResult.Success, TransactionResult.InsufficientFunds {

    record Success(String transactionId, Instant timestamp) implements TransactionResult{}

    record InsufficientFunds(BigDecimal requested, BigDecimal available) implements TransactionResult {

    }
}
