package org.example.stablevalue;

import java.util.Set;

public class StableMapTest {

    static void main() {
        var cities = Set.of("serrana", "serra-azul");

        var cityToUf = StableValue.map(cities, city -> setUf(city));

        IO.println(cityToUf.get("serrana"));

        var cityToUfFunc = StableValue.function(cities, city -> setUf(city));

        IO.println(cityToUfFunc.apply("serrana"));
    }

    private static String setUf(final String city) {
        if (city.equals("serrana")) {
            return "sp";
        }

        return "mg";
    }
}
