package util.inputdata;

import dto.MatchInfo;
import org.junit.jupiter.api.Test;

import static domain.constants.MatchOutcome.A;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchParserTest {
	@Test
	public void testParseMatch() {
		String matchAsString = "abae2255-4255-4304-8589-737cdff61640,1.45,0.75,A";
		MatchInfo matchInfo = new MatchParser().parseMatchInfoFromString(matchAsString);
		assertMatchWasParsedCorrectly(matchInfo);
	}

	private void assertMatchWasParsedCorrectly(MatchInfo matchInfo) {
		assertEquals("abae2255-4255-4304-8589-737cdff61640", matchInfo.getId().toString());
		assertEquals(1.45, matchInfo.getMatchData().getABetRate());
		assertEquals(0.75, matchInfo.getMatchData().getBBetRate());
		assertEquals(A, matchInfo.getMatchData().getMatchOutcome());
	}
}
