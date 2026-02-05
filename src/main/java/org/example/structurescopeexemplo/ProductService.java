package org.example.structurescopeexemplo;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

@SuppressWarnings("ALL")
public class ProductService {

    public ProductInfo fetchProductInfo(final Long productId, boolean shouldFail) {
        Instant start = Instant.now();
        log("fetching product & reviews for id: " + productId);

        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.anySuccessfulResultOrThrow())) {
            StructuredTaskScope.Subtask<Product> productTask = scope.fork(() -> shouldFail ? fetchProductThatFails(productId) : fetchProduct(productId));
            StructuredTaskScope.Subtask<List<Review>> reviewsTask = scope.fork(() -> fetchReviews(productId));

            log("... scope joining. Waiting for substasks");
            scope.join();
            log("...Scioe joined successfuly");

            return new ProductInfo(productTask.get(), reviewsTask.get());
        } catch (InterruptedException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            log("error processing product info for id: " + productId + ": " + cause.getMessage());

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            throw new ProductServiceException("fetch failed for id: " + productId, cause);
        } finally {
            Instant end = Instant.now();
            log("total time taken: " + Duration.between(start, end).toMillis() + "ms");
        }
    }

    public Product fetchProductTest(long productId) {
        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Product>anySuccessfulResultOrThrow())) {
            scope.fork(() -> fetchProductFromDataBase(productId));
            scope.fork(() -> fetchProductFromCache(productId));
            scope.fork(() -> fetchProductFromApi(productId));

            return scope.join();
        } catch (InterruptedException e) {
            log("error processing fetch product " + e.getMessage());
            throw new ProductServiceException(e.getMessage());
        }
    }

    private Product fetchProductFromCache(long productId) throws InterruptedException {
        //throw new RuntimeException();
        log("-> checking cache... (will take 500ms)");
        sleepForAWhile(Duration.ofMillis(500));
        log("-> cache has the result!");
        return new Product(productId, "product from cache", "");
    }

    private Product fetchProductFromDataBase(long productId) throws InterruptedException {
        log("-> checking database... (will take 500ms)");
        sleepForAWhile(Duration.ofSeconds(2));
        log("-> cache has the result!");
        return new Product(productId, "product from database", "");
    }

    private Product fetchProductFromApi(long productId) throws InterruptedException {
        log("-> checking api... (will take 500ms)");
        sleepForAWhile(Duration.ofSeconds(3));
        log("-> cache has the result!");
        return new Product(productId, "product from api", "");
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

    private Product fetchProductThatFails(long productId) {
        log("-> fetching products details... (will fail)");
        throw new ProductServiceException("product id " + productId + " not found");
    }

    private List<Review> fetchReviews(Long productId) {
        log("fetching reviews for id: " + productId);
        if (productId == 1L) {
            log("reviews for id: " + productId + " - simulating quick failure after 1 second");
            sleepForAWhile(Duration.ofSeconds(1));
            throw new ProductServiceException("simulated failure fetching reviews for product " + productId);
        }

        log("reviews for id: " + productId + " -simulating network call (2 seconds).");
        sleepForAWhile(Duration.ofSeconds(2));
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

        /*long testProductId = 2L;
        log("attempting to fetch product info for ID: " + testProductId);*/
        try {
            //ProductInfo productInfo = productService.fetchProductInfo(testProductId, false);
            IO.println(productService.fetchProductTest(2L));
        } catch (ProductServiceException e) {
            log("service error: " + e.getMessage() + (e.getCause() != null ? " | Caused by: " + e.getCause().getMessage() : ""));
        }
    }
}
