package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NumerosDuplicados {

    static void main() {
        var list = Arrays.asList(1, 2, 3, 4, 2);

        var result = new HashSet<>();

        if (result.size() != list.size()) {
            //IO.println("existe numero duplicado");
        }

        list.forEach(v -> {
            if (!result.add(v)) {
                IO.println("numero ja existe " + v);
            }
        });

        list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream().filter(v ->  v.getValue() > 1)
                .forEach(v ->  IO.println("duplicado " + v.getValue()));

        list.stream().collect(Collectors.partitioningBy(v ->  v % 2 == 0))
                .forEach((isPar, valores) ->  IO.println(isPar + " " + valores));

    }
}
