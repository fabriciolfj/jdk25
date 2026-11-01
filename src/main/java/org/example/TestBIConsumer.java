package org.example;

import java.util.function.BiConsumer;

public class TestBIConsumer {

    static void main() {
        var biConsumer = apply();

        biConsumer.accept("test1", "test2");
    }

    private static <T, U> BiConsumer<T, U> apply() {
        return (t, u) -> IO.println(u + " " + t);
    }



}
