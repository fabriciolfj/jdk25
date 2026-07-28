package org.example.generics;

public class Simulacao {

    static void main() {
        var page = new Page<Product>();
        IO.println(page.list);

        var product = new Product("test");

        page.list.add(product);

        var order = new Order("test");

    }
}
