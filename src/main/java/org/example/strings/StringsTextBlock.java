package org.example.strings;

public class StringsTextBlock {

    static void main() {
        String value = """
                
                email enviado para %s
                """.formatted("fabricio");

        System.out.println(value);
    }
}
