package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("src", "main", "resources", "sample", "match_data.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
