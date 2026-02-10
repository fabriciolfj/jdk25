package org.example.scopeexceptionhandler;

import module java.base;

import static java.util.concurrent.StructuredTaskScope.*;
import static org.example.Utils.log;

public class BasicExceptionHandling {

    public String fetchUserData(String userId) {
        try (var scope = open(Joiner.<String>allSuccessfulOrThrow())) { // ①
            var profileTask = scope.fork(() -> fetchUserProfile(userId));
            var preferencesTask = scope.fork(() -> fetchUserPreferences(userId));

            var results = scope.join();

            // Process successful results
            return results.map(Subtask::get)
                    .collect(Collectors.joining(", "));

        } catch (FailedException e) { // ②
            log("Task failed: " + e.getCause().getMessage());
            return "Error: Unable to fetch user data";
        } catch (InterruptedException e) { // ③
            Thread.currentThread().interrupt();
            throw new RuntimeException("Operation interrupted", e);
        }
    }

    private String fetchUserProfile(String userId)
            throws InterruptedException {
        Thread.sleep(Duration.ofMillis(200));
        if ("invalid".equals(userId)) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return "Profile for " + userId;
    }

    private String fetchUserPreferences(String userId)
            throws InterruptedException {
        Thread.sleep(Duration.ofMillis(150));
        if (userId.startsWith("blocked")) {
            throw new SecurityException("User access blocked");
        }
        return "Preferences for " + userId;
    }

    void main() {
        var demo = new BasicExceptionHandling();
        demo.fetchUserData("invalid");
    }
}
