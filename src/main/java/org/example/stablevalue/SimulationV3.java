package org.example.stablevalue;

import java.util.function.Supplier;

public class SimulationV3 {

    private static final Supplier<Person> personObject = StableValue.supplier(Person::new);

    static void main() {
        var person = personObject.get();

        System.out.println(person);
    }
}
