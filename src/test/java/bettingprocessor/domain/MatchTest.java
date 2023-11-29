package bettingprocessor.domain;

import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.domain.objects.Player;
import bettingprocessor.dto.Betting;
import bettingprocessor.dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.util.objectgenerator.MatchGenerator;
import bettingprocessor.util.objectgenerator.PlayerGenerator;

import static org.junit.jupiter.api.Assertions.*;

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
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, new Casino());

        assertEquals(1.3, match.getMatchData().getABetRate());
        assertEquals(0.75, match.getMatchData().getBBetRate());
        assertEquals(MatchOutcome.A, match.getMatchData().getMatchOutcome());
    }

    @Test
    public void testBettingWasCreatedCorrectlyForMatch() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.A);

        assertOneBettingWasCreatedForMatchByPlayerAndIsCorrect(match, player);
    }

    private static void assertOneBettingWasCreatedForMatchByPlayerAndIsCorrect(Match match, Player player) {
        assertEquals(1, match.getBettings().size());

        Betting singleBettingOnMatch = match.getBettings().get(0);
        assertEquals(player, singleBettingOnMatch.getPlayer());
        assertEquals(match, singleBettingOnMatch.getMatch());
        assertEquals(30, singleBettingOnMatch.getAmount());
    }

    @Test
    public void testPlayerBetsOnASideAndGetsAProfitOnVictory() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

		casino.addPlayer(player);
        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.A);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(109, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnBSideAndGetsAProfitOnVictory() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

	    casino.addPlayer(player);
        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(92, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnASideAndLosesMoneyOnLoss() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

	    casino.addPlayer(player);
        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.A);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnBSideAndLosesMoneyOnLoss() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

	    casino.addPlayer(player);
        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsAndGetsHisBetMoneyBackOnDraw() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithDraw, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

	    casino.addPlayer(player);
        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(100, player.getCoins());
    }

    @Test
    public void testManyPlayersBetOnAMatch() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        Player playerOne = PlayerGenerator.generatePlayerWithRandomID(casino);
        Player playerTwo = PlayerGenerator.generatePlayerWithRandomID(casino);

	    casino.addPlayer(playerOne);
		casino.addPlayer(playerTwo);
        playerOne.deposit(100);
        playerTwo.deposit(100);
        playerOne.betOnMatch(30, match, BettingSide.A);
        playerTwo.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(2, match.getBettings().size());
        assertEquals(109, playerOne.getCoins());
        assertEquals(70, playerTwo.getCoins());
    }

    @Test
    public void testPlayerBetsAndIsConsideredIllegal() {
		Casino casino = new Casino();
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        Player player = PlayerGenerator.generatePlayerWithRandomID(casino);

	    casino.addPlayer(player);
        player.deposit(100);
        player.betOnMatch(150, match, BettingSide.A);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertTrue(match.getCasino().getIllegitimatePlayers().contains(player));
    }
}
