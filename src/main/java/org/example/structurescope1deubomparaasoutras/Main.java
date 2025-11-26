package org.example.structurescope1deubomparaasoutras;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        System.out.println(buildTestingTeam());
    }

    public static TestingTeam buildTestingTeam() throws InterruptedException, ExecutionException {

        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.anySuccessfulResultOrThrow())) {

            scope.fork(() -> fetchTester(1));
            scope.fork(() -> fetchTester(2));
            scope.fork(() -> fetchTester(3));

            return new TestingTeam((String) scope.join());
        }
    }

    public static String fetchTester(int id) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        // intentionally added a delay of 1-5 seconds
        Thread.sleep(Duration.ofMillis(ThreadLocalRandom.current().nextLong(5000)));

        HttpRequest requestGet = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://reqres.in/api/users/" + id))
                .build();

        HttpResponse<String> responseGet = client.send(
                requestGet, HttpResponse.BodyHandlers.ofString());

        if (responseGet.statusCode() == 200) {
            return responseGet.body();
        }

        throw new UserNotFoundException("Code: " + responseGet.statusCode());
    }
}
