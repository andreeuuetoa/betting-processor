package domain;

import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.MatchOutcome;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasinoTest {
    private static MatchData sampleMatchDataWithASideWinning;
    private static MatchData sampleMatchDataWithBSideWinning;
    private static MatchData sampleMatchDataWithDraw;

    @BeforeAll
    public static void createSampleMatchData() {
        sampleMatchDataWithASideWinning = new MatchData(1.3, 0.75, MatchOutcome.A);
        sampleMatchDataWithBSideWinning = new MatchData(1.3, 0.75, MatchOutcome.B);
        sampleMatchDataWithDraw = new MatchData(1.3, 0.75, MatchOutcome.DRAW);
    }

    @Test
    public void testCasinoInitialBalanceIsZero() {
        Casino casino = new Casino();
        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testCasinoCanHostMatches() {
        Casino casino = new Casino();
        Match matchOne = new Match(sampleMatchDataWithASideWinning);
        Match matchTwo = new Match(sampleMatchDataWithBSideWinning);
        Match matchThree = new Match(sampleMatchDataWithDraw);

        casino.addMatch(matchOne);
        casino.addMatch(matchTwo);
        casino.addMatch(matchThree);

        assertEquals(3, casino.getMatches().size());
    }
}
