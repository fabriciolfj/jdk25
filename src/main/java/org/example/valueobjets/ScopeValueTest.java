package org.example.valueobjets;

public class ScopeValueTest {

    final ScopedValue<String> PRODUCT = ScopedValue.newInstance();

    void main() {
        ScopedValue.where(PRODUCT, "arroz").run(() -> {

            ScopedValue.where(PRODUCT, PRODUCT.get())
                    .run(() -> {
                        IO.println("Product found " + PRODUCT.get());
                    });
        });
    };
}
