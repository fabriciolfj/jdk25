package org.example.parallelstrategy;

import java.util.concurrent.StructuredTaskScope;

public class StructureScopeTest {

    static void main() {
        try(StructuredTaskScope scope = StructuredTaskScope.open()) {
            StructuredTaskScope.Subtask<String> subtask1 =scope.fork(() -> fetchData());
            StructuredTaskScope.Subtask<String> subtask2 =scope.fork(() -> fetchData2());

            scope.join();
            var result =  subtask2.get() + subtask1.get();
            IO.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }
    }

    static String fetchData() {
        return "ok";
    }

    static String fetchData2() {
        return "teste";
    }
}
