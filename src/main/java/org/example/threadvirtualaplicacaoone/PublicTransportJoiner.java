package org.example.threadvirtualaplicacaoone;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class  PublicTransportJoiner
        implements StructuredTaskScope.Joiner<List<PublicTransportOffer>, PublicTransportOffer>, AutoCloseable {

    private final List<List<PublicTransportOffer>> results = new CopyOnWriteArrayList<>();
    private final List<Throwable> exceptions = new CopyOnWriteArrayList<>();

    @Override
    public boolean onComplete(Subtask<? extends List<PublicTransportOffer>> subtask) {

        switch (subtask.state()) {
            case SUCCESS ->
                    results.add(subtask.get());
            case FAILED ->
                    exceptions.add(subtask.exception());
            case UNAVAILABLE ->
                    throw new IllegalStateException("Subtask may still running ...");
        }

        return false; // não cancela, continua esperando todas as subtasks
    }

    @Override
    public PublicTransportOffer result() {

        return results.stream()
                .flatMap(List::stream)
                .min(Comparator.comparing(PublicTransportOffer::goTime))
                .orElseThrow(this::wrappingExceptions);
    }

    private PublicTransportException wrappingExceptions() {

        PublicTransportException exceptionWrapper
                = new PublicTransportException("Public transport exception");
        exceptions.forEach(exceptionWrapper::addSuppressed);

        return exceptionWrapper;
    }

    @Override
    public void close() throws Exception {

    }
}