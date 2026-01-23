package org.example.parallelstrategy;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExampleInvokeAll {

    static void main() {
        try (var service = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<String>> task = List.of(
                    () -> resize(null, 0, 0),
                    () -> grayscale(null),
                    () -> rotate(null, 0.0));

            var results = service.invokeAll(task);
            int i = 0;
            for(Future<String> future: results) {
                //BufferedImage image = future.get();
                String name = future.get();
                IO.println("name " + name + " indice" + i);
                //ImageIO.write(image, "jpg", new File("test"  + i + ".jpg"));
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String resize(final String url, int width, int height) {
        return "test1";
    }

    static String grayscale(final String url) {
        return "test2";
    }

    static String rotate(final String url, double angle) {
        return "test3";
    }
}
