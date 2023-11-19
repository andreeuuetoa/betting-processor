package util.files;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MatchDataFileReader {

    public List<String> getMatchesAsStringsFromFileInPath(Path path) {
        List<String> matches = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                matches.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return matches;
    }
}
