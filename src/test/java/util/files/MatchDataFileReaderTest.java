package util.files;

import domain.constants.MatchOutcome;
import domain.objects.Match;
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
        Path path = Paths.get("src", "test", "resources", "one_match_data.txt");
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
        Path path = Paths.get("src", "test", "resources", "one_match_data.txt");
        List<Match> matches = MatchDataFileReader.createMatchesFromFileInPath(path);
        assertEquals(1, matches.size());
        Match match = matches.get(0);
        assertMatchWasCreatedCorrectly(match);
    }

    private void assertMatchWasCreatedCorrectly(Match match) {
        assertEquals("abae2255-4255-4304-8589-737cdff61640", match.getId().toString());
        assertEquals(1.45, match.getMatchData().getABetRate());
        assertEquals(0.75, match.getMatchData().getBBetRate());
        assertEquals(MatchOutcome.A, match.getMatchData().getMatchOutcome());
    }

    @Test
    public void testCreateMatchesFromGivenMatchData() {
        Path path = Paths.get("src", "test", "resources", "match_data.txt");
        List<Match> matches = MatchDataFileReader.createMatchesFromFileInPath(path);
        assertEquals(13, matches.size());
    }
}
