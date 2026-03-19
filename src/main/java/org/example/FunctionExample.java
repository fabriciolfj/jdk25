package org.example;

import java.util.function.BiFunction;

public class FunctionExample {

    static void main() {
        final BiFunction<Integer, Integer, Integer> function  = Integer::sum;

        IO.println(function.apply(4, 5));
    }
}
