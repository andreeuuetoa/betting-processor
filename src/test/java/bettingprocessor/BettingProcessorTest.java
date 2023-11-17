package bettingprocessor;

import domain.Match;
import domain.Player;
import dto.Betting;
import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.BettingSide;
import util.MatchOutcome;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BettingProcessorTest {
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
    public void testPlayerBetsOnASideAndGetsAProfitOnVictory() {
        Match sampleMatch = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.A);

        BettingProcessor bettingProcessor = new BettingProcessor(sampleMatchDataWithASideWinning);
        int moneyGotBack = bettingProcessor.calculateMoneyGotBackFromBetting(betting);

        assertEquals(39, moneyGotBack);
    }

    @Test
    public void testPlayerBetsOnBSideAndGetsAProfitOnVictory() {
        Match sampleMatch = new Match(sampleMatchDataWithBSideWinning);
        Player player = new Player();
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.B);

        BettingProcessor bettingProcessor = new BettingProcessor(sampleMatchDataWithBSideWinning);
        int moneyGotBack = bettingProcessor.calculateMoneyGotBackFromBetting(betting);

        assertEquals(22, moneyGotBack);
    }

    @Test
    public void testPlayerBetsOnASideAndLosesMoneyOnLoss() {
        Match sampleMatch = new Match(sampleMatchDataWithBSideWinning);
        Player player = new Player();
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.A);

        BettingProcessor bettingProcessor = new BettingProcessor(sampleMatchDataWithBSideWinning);
        int moneyGotBack = bettingProcessor.calculateMoneyGotBackFromBetting(betting);

        assertEquals(0, moneyGotBack);
    }

    @Test
    public void testPlayerBetsOnBSideAndLosesMoneyOnLoss() {
        Match sampleMatch = new Match(sampleMatchDataWithASideWinning);
        Player player = new Player();
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.B);

        BettingProcessor bettingProcessor = new BettingProcessor(sampleMatchDataWithASideWinning);
        int moneyGotBack = bettingProcessor.calculateMoneyGotBackFromBetting(betting);

        assertEquals(0, moneyGotBack);
    }

    @Test
    public void testPlayerBetsAndGetsHisBetMoneyBackOnDraw() {
        Match sampleMatch = new Match(sampleMatchDataWithDraw);
        Player player = new Player();
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.B);

        BettingProcessor bettingProcessor = new BettingProcessor(sampleMatchDataWithDraw);
        int moneyGotBack = bettingProcessor.calculateMoneyGotBackFromBetting(betting);

        assertEquals(30, moneyGotBack);
    }
}
