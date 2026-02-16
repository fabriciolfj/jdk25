package org.example.testscopevalue;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class Simulation {

    static void main() {
        var person = new Person("fabricio", 41);
        var nfService = new SendNotaFiscalService();
        var stockService = new SendReserveStock();

        var process = List.of(nfService, stockService);

        var simulation = new Simulation();
        var scope = ScopeValueCorrelationUtil.createScope();

        ScopedValue.where(scope, "1001")
                        .run(() -> {
                            try {
                                simulation.executeTasks(process, person);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
    }

    public void executeTasks(final List<ProcessByService> processByServices, final Person person) throws InterruptedException {

        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.allSuccessfulOrThrow())) {
            processByServices.forEach(p -> {
                scope.fork(() -> p.execute(person));
            });

            scope.join();
        }
    }
}
