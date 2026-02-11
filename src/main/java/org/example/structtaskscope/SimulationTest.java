package org.example.structtaskscope;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadFactory;

public class SimulationTest {

    static void main() {
        var simulation = new SimulationTest();
        var documento = new Document();

        ThreadFactory threadFactory = Thread.ofVirtual()
                .name("status-notificaiton-")
                .factory();
        Duration timeout = Duration.ofSeconds(1);
        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Document>anySuccessfulResultOrThrow(), c ->
                c.withThreadFactory(threadFactory).withTimeout(timeout).withName("test"))) {
            scope.fork(() -> simulation.sendEmail(documento));
            scope.fork(() -> simulation.sendNf(documento));

            var result = scope.join();
            IO.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Document sendNf(Document documento) {
        IO.println("emitindo nf");
        documento.setStatusNf("ok");

        IO.println(Thread.currentThread().getName());
        return documento;
    };

    private Document sendEmail(Document documento) {
        IO.println("enviando email");
        documento.setStatusEmail("ok");

        IO.println(Thread.currentThread().getName());
        return documento;
    }
}
