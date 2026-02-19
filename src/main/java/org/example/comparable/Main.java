package org.example.comparable;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        var transacoes = List.of(
                new Transacao("T1", 100.0, LocalDate.of(2024, 1, 15)),
                new Transacao("T2", 250.0, LocalDate.of(2025, 6, 20)),
                new Transacao("T3", 80.0,  LocalDate.of(2023, 11, 5)),
                new Transacao("T4", 500.0, LocalDate.of(2025, 2, 1))
        );

        // Sem passar nenhum Comparator — usa o compareTo da própria classe
        transacoes.stream()
                .sorted()
                .forEach(t -> System.out.printf("%s | %s | R$ %.2f%n", t.id(), t.data(), t.valor()));
    }
}
