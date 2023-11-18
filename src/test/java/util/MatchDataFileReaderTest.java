package util;

import domain.constants.MatchOutcome;
import domain.objects.Match;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchDataFileReaderTest {
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
