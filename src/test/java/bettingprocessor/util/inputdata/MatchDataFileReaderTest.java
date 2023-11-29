package bettingprocessor.util.inputdata;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchDataFileReaderTest {
    @Test
    public void testCreateMatchFromMatchDataWithOneRow() {
        Path path = Paths.get("src", "test", "resources", "matchdata", "one_match_data.txt");
        List<String> matchesAsStrings = new MatchDataFileReader(path).getMatchesAsStringsFromFile();
        assertEquals(1, matchesAsStrings.size());
        String matchAsString = matchesAsStrings.get(0);
        assertEquals("abae2255-4255-4304-8589-737cdff61640,1.45,0.75,A", matchAsString);
    }

    @Test
    public void testCreateMatchesFromGivenMatchData() {
        Path path = Paths.get("src", "test", "resources", "matchdata", "match_data.txt");
        List<String> matches = new MatchDataFileReader(path).getMatchesAsStringsFromFile();
        assertEquals(13, matches.size());
    }

	@Test
	public void testReaderThrowsExceptionIfCannotFindTheFile() {
		Path path = Paths.get("non-existant-path", "foo", "bar", "match_data.txt");
		assertThrows(RuntimeException.class, () -> new MatchDataFileReader(path).getMatchesAsStringsFromFile());
	}
}
