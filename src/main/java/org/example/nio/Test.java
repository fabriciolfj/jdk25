package org.example.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Test {

    static void main() throws IOException {
        readFile();
    }

    public static void readFile() throws IOException {
        Path path = Paths.get("arquivo.txt");

        // Ler todas as linhas
        List<String> lines = Files.readAllLines(path);

        // Ler todo o conteúdo
        String content = Files.readString(path);

        // Stream de linhas (para arquivos grandes)
        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(System.out::println);
        }
    }
}


