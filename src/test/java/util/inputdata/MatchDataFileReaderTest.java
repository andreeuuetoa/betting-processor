package util.inputdata;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchDataFileReaderTest {
    @Test
    public void testOneMatchDataFileExistsAndContainsData() {
        Path path = Paths.get("src", "test", "resources", "matchdata", "one_match_data.txt");
        String oneMatchData = "abae2255-4255-4304-8589-737cdff61640,1.45,0.75,A";
        assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            assertEquals(oneMatchData, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateMatchFromMatchDataWithOneRow() {
        Path path = Paths.get("src", "test", "resources", "matchdata", "one_match_data.txt");
        List<String> matchesAsStrings = new MatchDataFileReader().getMatchesAsStringsFromFileInPath(path);
        assertEquals(1, matchesAsStrings.size());
        String matchAsString = matchesAsStrings.get(0);
        assertEquals("abae2255-4255-4304-8589-737cdff61640,1.45,0.75,A", matchAsString);
    }

    @Test
    public void testCreateMatchesFromGivenMatchData() {
        Path path = Paths.get("src", "test", "resources", "matchdata", "match_data.txt");
        List<String> matches = new MatchDataFileReader().getMatchesAsStringsFromFileInPath(path);
        assertEquals(13, matches.size());
    }
}
