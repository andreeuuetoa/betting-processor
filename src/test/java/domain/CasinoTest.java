package domain;

import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.BettingSide;
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

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndPlayerGetsMoney() {
        Casino casino = new Casino();
        Match match = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(115, player.getCoins());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoLosesMoney() {
        Casino casino = new Casino();
        Match match = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(-15, casino.getBalance());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoGetsAProfit() {
        Casino casino = new Casino();
        Match match = new Match(sampleMatchDataWithBSideWinning);
        Player player = new Player();
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(50, casino.getBalance());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoBalanceStaysConstant() {
        Casino casino = new Casino();
        Match match = new Match(sampleMatchDataWithDraw);
        Player player = new Player();
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }
}
