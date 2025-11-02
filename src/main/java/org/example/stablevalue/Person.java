package org.example.stablevalue;

public record Person(String name) {

    public Person() {
        this("Test");
    }
}
