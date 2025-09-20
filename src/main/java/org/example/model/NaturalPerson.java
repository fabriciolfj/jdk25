package org.example.model;

import java.util.Objects;

public non-sealed class NaturalPerson extends Person {

    public NaturalPerson(String name, String document) {
        Objects.nonNull(name);
        Objects.nonNull(document);
        super(name, document);
    }

    @Override
    public String getDocument() {
        return super.getDocument();
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
