package util.inputdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MatchDataFileReader {
	private final Path matchDataFilePath;

	public MatchDataFileReader(Path path) {
		matchDataFilePath = path;
	}

	public List<String> getMatchesAsStringsFromFile() {
        List<String> matches = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(matchDataFilePath)) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
	                break;
                }
                matches.add(line);
            }
        } catch (IOException e) {
	        throw new RuntimeException("File in path: " + matchDataFilePath.toString() + " was not found.");
        }
        return matches;
    }
}
