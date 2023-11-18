package domain;

import domain.objects.Match;
import domain.objects.Player;
import dto.Betting;
import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import domain.constants.BettingSide;
import domain.constants.MatchOutcome;
import util.objectgenerators.MatchGenerator;
import util.objectgenerators.PlayerGenerator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning);

        assertEquals(1.3, match.getMatchData().getABetRate());
        assertEquals(0.75, match.getMatchData().getBBetRate());
        assertEquals(MatchOutcome.A, match.getMatchData().getMatchOutcome());
    }

    @Test
    public void testBettingWasCreatedCorrectlyForMatch() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

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
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.A);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(109, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnBSideAndGetsAProfitOnVictory() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(92, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnASideAndLosesMoneyOnLoss() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.A);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnBSideAndLosesMoneyOnLoss() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsAndGetsHisBetMoneyBackOnDraw() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithDraw);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

        player.deposit(100);
        player.betOnMatch(30, match, BettingSide.B);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertEquals(100, player.getCoins());
    }

    @Test
    public void testManyPlayersBetOnAMatch() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning);
        Player playerOne = PlayerGenerator.generatePlayerWithRandomID();
        Player playerTwo = PlayerGenerator.generatePlayerWithRandomID();

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
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning);
        Player player = PlayerGenerator.generatePlayerWithRandomID();

        player.deposit(100);
        player.betOnMatch(150, match, BettingSide.A);
        match.calculateCasinoProfit();
        match.payToPlayers();

        assertTrue(match.getIllegalPlayers().contains(player));
    }
}
