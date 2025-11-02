package org.example.stablevalue;

public class SimulationV2 {

    static void main() {
        StableValue<String> value = StableValue.of();


        System.out.println(value.isSet());

        value.orElseSet(() -> "test");

        System.out.println(value);
    }
}
