package org.example.model;

public sealed class Person permits NaturalPerson {
    private String name;
    private String document;

    public Person(String name, String document) {
        this.name = name;
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", document='" + document + '\'' +
                '}';
    }
}
