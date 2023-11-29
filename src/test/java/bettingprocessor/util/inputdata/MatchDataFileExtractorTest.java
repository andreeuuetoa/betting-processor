package bettingprocessor.util.inputdata;

import bettingprocessor.dto.MatchInfo;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchDataFileExtractorTest {
	@Test
	public void testExtractMatchFromFileWithOneMatch() {
		Path path = Paths.get("src", "test", "resources", "matchdata", "one_match_data.txt");
		List<MatchInfo> matchesFromFile = new MatchDataFileExtractor(path).getMatchInfo();
		assertEquals(1, matchesFromFile.size());
	}

	@Test
	public void testExtractMatchesFromFileWithMultipleMatches() {
		Path path = Paths.get("src", "test", "resources", "matchdata", "match_data.txt");
		List<MatchInfo> matchesFromFile = new MatchDataFileExtractor(path).getMatchInfo();
		assertEquals(13, matchesFromFile.size());
	}
}
