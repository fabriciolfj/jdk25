package org.example.structurescopeexemplo;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductService {

    public ProductInfo fetchProductInfo(final Long productId) {
        log("fetching product & reviews for id: " + productId);

        try(var service = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Product> productTask = service.submit(() -> fetchProduct(productId));
            Future<List<Review>> reviewsTask = service.submit(() -> fetchReviews(productId));

            Product product = productTask.get();
            log("product retrieved for id: " + productId);

            var reviews = reviewsTask.get();
            log("reviews retrieved for id: " + productId);
            log("all info fetched for id: " + productId);

            return new ProductInfo(product, reviews);
        } catch (ExecutionException | InterruptedException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            log("error processing product info for id: " + productId + ": " + cause.getMessage());

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            throw new ProductServiceException("fetch failed for id: " + productId, cause);
        }
    }

    private Product fetchProduct(Long productId) {
        log("fetching product id" + productId);
        if (productId == 1L) {
            log("product id: " + productId + " -simulating long network call (5 seconds).");
            sleepForAWhile(Duration.ofSeconds(5));
            log("product id: " + productId + " fetch complete.");
            return new Product(productId, "long-fetched product", "this product taks time to fetch");
        }

        log("product id: " + productId + " -simulating long network call (1 seconds).");
        sleepForAWhile(Duration.ofSeconds(1));
        log("product id: " + productId + " fetch complete.");
        return new Product(productId, "sample product", "this grat product description");
    }

    private List<Review> fetchReviews(Long productId) {
        log("fetching reviews for id: " + productId);
        if (productId == 1L) {
            log("reviews for id: " + productId + " - simulating quick failure after 1 second");
            sleepForAWhile(Duration.ofSeconds(1));
            throw new ProductServiceException("simulated failure fetching reviews for product " + productId);
        }

        log("reviews for id: " + productId + " -simulating network call (2 seconds).");
        List<Review> reviews = List.of(
                new Review(1L, "excellent!", 5, productId),
                new Review(2L, "good value!", 4, productId)
        );
        log("fetched reviews for id: " + productId);
        return reviews;
    }

    private void sleepForAWhile(final Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("thread interrupted during sleep", e);
        }
    }

    private static void log(final String message) {
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.isVirtual() ? "VThread[#" + currentThread.threadId() + "]" : currentThread.getName();
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        System.out.printf("%s %s-15s: %s%n", currentTime, threadName, message);
    }

    static void main() {
        ProductService productService = new ProductService();
        long testProductId = 2L;
        log("attempting to fetch product info for ID: " + testProductId);
        try {
            ProductInfo productInfo = productService.fetchProductInfo(testProductId);
        } catch (ProductServiceException e) {
            log("service error: " + e.getMessage() + (e.getCause() != null ? " | Caused by: " + e.getCause().getMessage() : ""));
        }
    }
}
