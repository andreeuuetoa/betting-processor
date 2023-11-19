package returnmoneycalculator;

import domain.objects.Casino;
import domain.objects.Match;
import domain.objects.Player;
import dto.Betting;
import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.constants.BettingSide;
import domain.constants.MatchOutcome;
import util.objectgenerator.MatchGenerator;
import util.objectgenerator.PlayerGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnMoneyCalculatorTest {
    private static MatchData sampleMatchDataWithASideWinning;
    private static MatchData sampleMatchDataWithBSideWinning;
    private static MatchData sampleMatchDataWithDraw;
    private Player player;
	private Casino casino;

    @BeforeEach
    public void createNewPlayerAndCasino() {
		casino = new Casino();
        player = PlayerGenerator.generatePlayerWithRandomID(casino);
    }

    @BeforeAll
    public static void createSampleMatchData() {
        sampleMatchDataWithASideWinning = new MatchData(1.3, 0.75, MatchOutcome.A);
        sampleMatchDataWithBSideWinning = new MatchData(1.3, 0.75, MatchOutcome.B);
        sampleMatchDataWithDraw = new MatchData(1.3, 0.75, MatchOutcome.DRAW);
    }

    @Test
    public void testPlayerBetsOnASideAndGetsAProfitOnVictory() {
        Match sampleMatch = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.A);

        ReturnMoneyCalculator returnMoneyCalculator = new ReturnMoneyCalculator(sampleMatchDataWithASideWinning);
        int moneyGotBack = returnMoneyCalculator.calculateMoneyGotBackFromBetting(betting);

        assertEquals(39, moneyGotBack);
    }

    @Test
    public void testPlayerBetsOnBSideAndGetsAProfitOnVictory() {
        Match sampleMatch = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.B);

        ReturnMoneyCalculator returnMoneyCalculator = new ReturnMoneyCalculator(sampleMatchDataWithBSideWinning);
        int moneyGotBack = returnMoneyCalculator.calculateMoneyGotBackFromBetting(betting);

        assertEquals(22, moneyGotBack);
    }

    @Test
    public void testPlayerBetsOnASideAndLosesMoneyOnLoss() {
        Match sampleMatch = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.A);

        ReturnMoneyCalculator returnMoneyCalculator = new ReturnMoneyCalculator(sampleMatchDataWithBSideWinning);
        int moneyGotBack = returnMoneyCalculator.calculateMoneyGotBackFromBetting(betting);

        assertEquals(0, moneyGotBack);
    }

    @Test
    public void testPlayerBetsOnBSideAndLosesMoneyOnLoss() {
        Match sampleMatch = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.B);

        ReturnMoneyCalculator returnMoneyCalculator = new ReturnMoneyCalculator(sampleMatchDataWithASideWinning);
        int moneyGotBack = returnMoneyCalculator.calculateMoneyGotBackFromBetting(betting);

        assertEquals(0, moneyGotBack);
    }

    @Test
    public void testPlayerBetsAndGetsHisBetMoneyBackOnDraw() {
        Match sampleMatch = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithDraw, casino);
        player.deposit(100);
        Betting betting = new Betting(player, sampleMatch, 30, BettingSide.B);

        ReturnMoneyCalculator returnMoneyCalculator = new ReturnMoneyCalculator(sampleMatchDataWithDraw);
        int moneyGotBack = returnMoneyCalculator.calculateMoneyGotBackFromBetting(betting);

        assertEquals(30, moneyGotBack);
    }
}
