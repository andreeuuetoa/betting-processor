package util.files;

import dto.MatchInfo;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static domain.constants.MatchOutcome.A;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchDataFileExtractorTest {
	@Test
	public void testExtractMatchFromFileWithOneMatch() {
		Path path = Paths.get("src", "test", "resources", "matchdata", "one_match_data.txt");
		List<MatchInfo> matchesFromFile = new MatchDataFileExtractor().extractMatchInfoFromFileInPath(path);
		assertEquals(1, matchesFromFile.size());
		MatchInfo parsedMatchInfo = matchesFromFile.get(0);
		assertMatchInfoWasParsedCorrectly(parsedMatchInfo);
	}

	private void assertMatchInfoWasParsedCorrectly(MatchInfo parsedMatchInfo) {
		assertEquals("abae2255-4255-4304-8589-737cdff61640", parsedMatchInfo.getId().toString());
		assertEquals(1.45, parsedMatchInfo.getMatchData().getABetRate());
		assertEquals(0.75, parsedMatchInfo.getMatchData().getBBetRate());
		assertEquals(A, parsedMatchInfo.getMatchData().getMatchOutcome());
	}

	@Test
	public void testExtractMatchesFromFileWithMultipleMatches() {
		Path path = Paths.get("src", "test", "resources", "matchdata", "match_data.txt");
		List<MatchInfo> matchesFromFile = new MatchDataFileExtractor().extractMatchInfoFromFileInPath(path);
		assertEquals(13, matchesFromFile.size());
	}
}
