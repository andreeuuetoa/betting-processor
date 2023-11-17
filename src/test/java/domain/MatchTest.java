package domain;

import dto.Betting;
import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.BettingSide;
import util.MatchOutcome;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTest {
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
    public void testCreateSampleMatch() {
        Match sampleMatch = new Match(sampleMatchDataWithASideWinning);

        assertEquals(1.3, sampleMatch.getMatchData().getABetRate());
        assertEquals(0.75, sampleMatch.getMatchData().getBBetRate());
        assertEquals(MatchOutcome.A, sampleMatch.getMatchData().getMatchOutcome());
    }

    @Test
    public void testBettingWasCreatedCorrectlyForMatch() {
        Match sampleMatch = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch, BettingSide.A);

        assertOneBettingWasCreatedForMatchByPlayerAndIsCorrect(sampleMatch, player);
    }

    private static void assertOneBettingWasCreatedForMatchByPlayerAndIsCorrect(Match sampleMatch, Player player) {
        assertEquals(1, sampleMatch.getBettings().size());

        Betting singleBettingOnMatch = sampleMatch.getBettings().get(0);
        assertEquals(player, singleBettingOnMatch.getPlayer());
        assertEquals(sampleMatch, singleBettingOnMatch.getMatch());
        assertEquals(30, singleBettingOnMatch.getAmount());
    }

    @Test
    public void testPlayerBetsOnASideAndGetsAProfitOnVictory() {
        Match sampleMatch = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch, BettingSide.A);

        sampleMatch.payToPlayers();
        assertEquals(109, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnBSideAndGetsAProfitOnVictory() {
        Match sampleMatch = new Match(sampleMatchDataWithBSideWinning);
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch, BettingSide.B);

        sampleMatch.payToPlayers();
        assertEquals(92, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnASideAndLosesMoneyOnLoss() {
        Match sampleMatch = new Match(sampleMatchDataWithBSideWinning);
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch, BettingSide.A);

        sampleMatch.payToPlayers();
        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnBSideAndLosesMoneyOnLoss() {
        Match sampleMatch = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch, BettingSide.B);

        sampleMatch.payToPlayers();
        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsAndGetsHisBetMoneyBackOnDraw() {
        Match sampleMatch = new Match(sampleMatchDataWithDraw);
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch, BettingSide.B);

        sampleMatch.payToPlayers();
        assertEquals(100, player.getCoins());
    }
}
