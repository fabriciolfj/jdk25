package org.example.parallelstrategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CreditCalculateMain {

    Credit calculteCredit(Long personId) {
        var person = getPerson(personId);
        var assets = getAssets(person);
        var liabilities = getLiabilities(person);
        importantWork();

        return calculateCredits(assets, liabilities);
    }


    Credit calculeCreditWithUnboundedThreads(Long personId) throws InterruptedException {
        var person = getPerson(personId);
        var assetsRef = new AtomicReference<List<Asset>>();
        var t1 = new Thread(() -> {
            var assets = getAssets(person);
            assetsRef.set(assets);
        });

        var liabilitiesRef = new AtomicReference<List<Liability>>();
        Thread t2 = new Thread(() -> {
            var liabilities = getLiabilities(person);
            liabilitiesRef.set(liabilities);
        });

        var t3 = new Thread(this::importantWork);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();

        var credit = calculateCredits(assetsRef.get(), liabilitiesRef.get());
        t3.join();

        return credit;
    }

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
