package org.example.parallelstrategy;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class CreditCalculateMain {

    Credit calculteCredit(Long personId) {
        var person = getPerson(personId);
        var assets = getAssets(person);
        var liabilities = getLiabilities(person);
        importantWork();

        return calculateCredits(assets, liabilities);
    }

    Credit calculeCreditWithUnboundedThreads(Long personId) throws InterruptedException, ExecutionException {

        try (ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor()) {
            var person = getPerson(personId);
            var assets = executors.submit(() -> getAssets(person));
            var liabilities = executors.submit(() -> getLiabilities(person));

            executors.submit(this::importantWork);

            return calculateCredits(assets.get(), liabilities.get());
        }
    }

    /*Mono<Credit>  calculeCreditWithUnboundedThreads(Long personId) throws InterruptedException, ExecutionException {
        Mono<Void> importantWorkMono = Mono.fromRunnable(this::importantWork);
        Mono<Person> personMono = Mono.fromSupplier(() -> getPerson(personId));
        Mono<List<Asset>> assetsMono = personMono.map(this::getAssets);
        Mono<List<Liability>> liabilitesMono = personMono.map(this::getLiabilities);

        return importantWorkMono.then(
                Mono.zip(assetsMono, liabilitesMono)
                        .map(tuple -> {
                            List<Asset> assets = tuple.getT1();
                            List<Liability> liabilities = tuple.getT2();
                            return calculateCredits(assets, liabilities);
                        })
        );
    }*/

    /*Credit calculeCreditWithUnboundedThreads(Long personId) throws InterruptedException, ExecutionException {
        return runAsync(() -> importantWork())
                .thenCompose(aVoid -> supplyAsync(() -> getPerson(personId)))
                .thenCombineAsync(supplyAsync(() -> getAssets(getPerson(personId))),
                        (person, asserts) -> calculateCredits(asserts, getLiabilities(person)))
                .get();
    }*/

    /*Credit calculeCreditWithUnboundedThreads(Long personId) throws InterruptedException, ExecutionException {

        try (ForkJoinPool joinPool = new ForkJoinPool()) {
            var person = getPerson(personId);
            var assets = joinPool.submit(() -> getAssets(person));
            var liabilities = joinPool.submit(() -> getLiabilities(person));

            joinPool.submit(this::importantWork);

            return calculateCredits(assets.get(), liabilities.get());
        }
    }
     */

    private Person getPerson(final Long personId) {
        simulateDelay(200);
        return new Person(personId, "John Doe");
    }

    private List<Asset> getAssets(final Person person) {
        simulateDelay(200);
        return List.of(
                new Asset("house", 300000),
                new Asset("Car", 25000)
        );
    }

    private List<Liability> getLiabilities(final Person person) {
        simulateDelay(200);
        return List.of(
                new Liability("Mortgage", 200000),
                new Liability("credit card", 5000));
    }

    private void importantWork() {
        simulateDelay(200);
        System.out.println("important work completed");
    }

    private Credit calculateCredits(List<Asset> assets, List<Liability> liabilities) {
        simulateDelay(200);
        double totalAssets = assets.stream().mapToDouble(Asset::value)
                .sum();
        double totalLiabilities = liabilities.stream()
                .mapToDouble(Liability::amount)
                .sum();

        double creditScore = (totalAssets - totalLiabilities) / 1000;
        return new Credit(creditScore);
    }

    private void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }
    }
}
