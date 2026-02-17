package org.example.simulacaoscopevalue;

public class Test {

    static ScopedValue<Integer> SCOPE_VALUE = ScopedValue.newInstance();


    static void main() {
        ScopedValue.where(SCOPE_VALUE, 1)
                .run(() -> {
                    test1();
                    test2();
                    test1();
                });

        IO.println(SCOPE_VALUE.get());
    }


    static void test1() {
        if (SCOPE_VALUE.isBound()) {
            ScopedValue.where(SCOPE_VALUE, SCOPE_VALUE.get() + 1);
        }

        IO.println(SCOPE_VALUE.get());
    }

    static void test2() {

        ScopedValue.where(SCOPE_VALUE, SCOPE_VALUE.get() + 1)
                .run(() -> test13());
    }

    static void test13() {
        IO.println(SCOPE_VALUE.get());
    }
}
