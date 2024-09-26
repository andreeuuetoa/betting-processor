package bettingprocessor.domain;

import bettingprocessor.domain.constants.PlayerActionType;
import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.domain.objects.Player;
import bettingprocessor.dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.util.objectgenerator.MatchGenerator;
import bettingprocessor.util.objectgenerator.PlayerGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
	private Casino casino;
    private static MatchData sampleMatchData;

    @BeforeEach
    public void createPlayerAndCasino() {
		casino = new Casino();
        player = PlayerGenerator.generatePlayerWithRandomID(casino);
    }

    @BeforeAll
    public static void createSampleMatchData() {
        sampleMatchData = new MatchData(1.00, 0.75, MatchOutcome.A);
    }

    @Test
    public void testPlayerInitialCoinbaseIsZero() {
        assertEquals(0, player.getCoins());
    }

	@Test
	public void testPlayerWasInitializedWithACasino() {
		assertEquals(casino, player.getCasino());
	}

    @Test
    public void testPlayerCanDeposit() {
        player.deposit(300);
        assertEquals(300, player.getCoins());
    }

    @Test
    public void testPlayerCannotWithdrawFromEmptyCoinbase() {
        player.withdraw(100);
		assertTrue(casino.getIllegitimatePlayers().contains(player));
    }

    @Test
    public void testPlayerCannotWithdrawFromInsufficientCoinbase() {
        player.deposit(30);
		player.withdraw(100);
		assertTrue(casino.getIllegitimatePlayers().contains(player));
    }

    @Test
    public void testPlayerCoinbaseDecreasesUponWithdrawal() {
        player.deposit(300);
        player.withdraw(100);
        assertEquals(200, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnMatchAndCoinbaseDecreases() {
        player.deposit(100);
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData, casino);
        player.betOnMatch(30, match, BettingSide.A);
        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsWithEmptyCoinbaseAndCoinbaseRemainsConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData, casino);
        player.betOnMatch(50, match, BettingSide.A);
        assertEquals(0, player.getCoins());
    }

    @Test
    public void testPlayerBetsWithInsufficientCoinbaseAndCoinbaseRemainsConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData, casino);
        player.deposit(30);
        player.betOnMatch(50, match, BettingSide.A);
        assertEquals(30, player.getCoins());
    }

    @Test
    public void testPlayerBetsTwiceOnAMatchAndCoinbaseRemainsConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData, casino);
        player.deposit(300);
        player.betOnMatch(100, match, BettingSide.A);
        player.betOnMatch(50, match, BettingSide.A);
        assertEquals(200, player.getCoins());
    }
}
