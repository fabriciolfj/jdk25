package org.example.exemploscopevalue;

public class Test {

    private static final ScopedValue TEST = ScopedValue.newInstance();

    static void main() {
        ScopedValue.where(TEST, "test")
                .run(() -> {
                    IO.println(TEST.get());
                });
    }
}
