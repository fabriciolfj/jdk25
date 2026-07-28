package org.example;

import java.util.concurrent.StructuredTaskScope;

import static java.util.concurrent.StructuredTaskScope.Joiner.anySuccessfulResultOrThrow;

public class SimulationScopeStructure {


    static void main() {

        try (var scope = StructuredTaskScope.open(anySuccessfulResultOrThrow())) {
            notificaitonEmail();
            notificationSms();
        }
    }

    static void notificaitonEmail() {
        IO.println("send email");
        throw new RuntimeException();
    }

    static void notificationSms() {
        IO.println("send sms");
    }
}
