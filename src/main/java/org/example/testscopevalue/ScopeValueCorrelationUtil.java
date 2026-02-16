package org.example.testscopevalue;


public class ScopeValueCorrelationUtil {

    private static ScopedValue<String> SCOPE;

    private ScopeValueCorrelationUtil() {

    }

    public static ScopedValue<String> createScope() {
        if (SCOPE == null) {
            SCOPE = ScopedValue.newInstance();
        }

        return SCOPE;
    }

    public  static String getValue() {
        return SCOPE.orElseThrow(() -> new RuntimeException("value not found"));
    }
}
