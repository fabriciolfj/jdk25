package org.example.shutdownonfailure;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        buildTestingTeam();
    }
    
    public static TestingTeam buildTestingTeam() throws InterruptedException, ExecutionException {
        
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow())) {

            Subtask<String> subtask3 = scope.fork(() -> fetchTester(Integer.MAX_VALUE));
            Subtask<String> subtask1 = scope.fork(() -> fetchTester(4));
            Subtask<String> subtask2 = scope.fork(() -> fetchTester(3));

            try {
                scope.join();
            } catch (StructuredTaskScope.FailedException e) {
                logger.throwing(e.getMessage(), "fail code", e);
            }

            logger.info(() -> "Subtask-1 state: " + subtask1.state());
            logger.info(() -> "Subtask-2 state: " + subtask2.state());
            logger.info(() -> "Subtask-3 state: " + subtask3.state());

            return new TestingTeam(subtask1.get(), subtask2.get(), subtask3.get());
        }
    }

    public static String fetchTester(int id) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

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
