package domain;

import domain.objects.Casino;
import domain.objects.Match;
import domain.objects.Player;
import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.constants.BettingSide;
import domain.constants.MatchOutcome;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasinoTest {
    private static MatchData sampleMatchDataWithASideWinning;
    private static MatchData sampleMatchDataWithBSideWinning;
    private static MatchData sampleMatchDataWithDraw;

    private Casino casino;
    private Player player;

    @BeforeEach
    public void createNewCasino() {
        casino = new Casino();
        player = new Player();
    }

    @BeforeAll
    public static void createSampleMatchData() {
        sampleMatchDataWithASideWinning = new MatchData(1.3, 0.75, MatchOutcome.A);
        sampleMatchDataWithBSideWinning = new MatchData(1.3, 0.75, MatchOutcome.B);
        sampleMatchDataWithDraw = new MatchData(1.3, 0.75, MatchOutcome.DRAW);
    }

    @Test
    public void testCasinoInitialBalanceIsZero() {
        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testCasinoCanHostMatches() {
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
        Match match = new Match(sampleMatchDataWithASideWinning);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(115, player.getCoins());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoLosesMoney() {
        Match match = new Match(sampleMatchDataWithASideWinning);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(-15, casino.getBalance());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoGetsAProfit() {
        Match match = new Match(sampleMatchDataWithBSideWinning);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(50, casino.getBalance());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoBalanceStaysConstant() {
        Match match = new Match(sampleMatchDataWithDraw);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testIllegalMoveByPlayerDoesNotImpactCasinoBalance() {
        Match match = new Match(sampleMatchDataWithASideWinning);
        player.deposit(100);
        player.betOnMatch(150, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testLegalMoveAndThenAnIllegalMoveDoesNotImpactCasinoBalance() {
        Match match = new Match(sampleMatchDataWithASideWinning);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);
        player.betOnMatch(100, match, BettingSide.A);

        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }
}
