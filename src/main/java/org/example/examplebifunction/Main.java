package org.example.examplebifunction;

import java.util.function.BiFunction;

public class Main {

    static void main() {
        BiFunction<Long, Long, String> function = (x, y) ->  x.toString() +  " - " + y;

        var resul = function.apply(2L, 4L);
        IO.println(resul);
    }
}
