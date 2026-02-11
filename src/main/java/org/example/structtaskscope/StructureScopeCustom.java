package org.example.structtaskscope;

import java.util.concurrent.StructuredTaskScope;

public class StructureScopeCustom<T> implements StructuredTaskScope.Joiner<T, T>{

    @Override
    public boolean onFork(StructuredTaskScope.Subtask<? extends T> subtask) {
        return StructuredTaskScope.Joiner.super.onFork(subtask);
    }

    @Override
    public boolean onComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
        return StructuredTaskScope.Joiner.super.onComplete(subtask);
    }

    @Override
    public T result() throws Throwable {
        return null;
    }
}
