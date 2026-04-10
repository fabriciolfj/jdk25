package org.example.enumtests;

public class Simulation {

    static void main() {
        var status = Status.ACTIVE;

        if(status.isAllowedChange(Status.ERROR)) {
            IO.println("pode");
        } else {
            IO.println("nao pode");
        }
    }
}
