package org.example.comparable;

import java.time.LocalDate;
import java.util.List;

public record Transacao(String id, double valor, LocalDate data)
        implements Comparable<Transacao> {

    @Override
    public int compareTo(Transacao transacao) {
        return transacao.data.compareTo(this.data);
    }
}

