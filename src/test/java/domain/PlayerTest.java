package domain;

import domain.constants.PlayerActionType;
import domain.objects.Match;
import domain.objects.Player;
import dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.constants.BettingSide;
import domain.constants.MatchOutcome;
import util.objectgenerator.MatchGenerator;
import util.objectgenerator.PlayerGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private static MatchData sampleMatchData;

    @BeforeEach
    public void createPlayer() {
        player = PlayerGenerator.generatePlayerWithRandomID();
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
    public void testPlayerCanDeposit() {
        player.deposit(300);
        assertEquals(300, player.getCoins());
    }

    @Test
    public void testPlayerCannotWithdrawFromEmptyCoinbase() {
        assertThrows(RuntimeException.class, () -> player.withdraw(100));
    }

    @Test
    public void testPlayerCannotWithdrawFromInsufficientCoinbase() {
        player.deposit(30);
        assertThrows(RuntimeException.class, () -> player.withdraw(100));
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
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData);
        player.betOnMatch(30, match, BettingSide.A);
        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerBetsWithEmptyCoinbaseAndCoinbaseRemainsConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData);
        player.betOnMatch(50, match, BettingSide.A);
        assertEquals(0, player.getCoins());
    }

    @Test
    public void testPlayerBetsWithInsufficientCoinbaseAndCoinbaseRemainsConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData);
        player.deposit(30);
        player.betOnMatch(50, match, BettingSide.A);
        assertEquals(30, player.getCoins());
    }

    @Test
    public void testPlayerBetsTwiceOnAMatchAndCoinbaseRemainsConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData);
        player.deposit(300);
        player.betOnMatch(100, match, BettingSide.A);
        player.betOnMatch(50, match, BettingSide.A);
        assertEquals(200, player.getCoins());
    }

	@Test
	public void testPlayerActDeposit() {
		player.act(PlayerActionType.DEPOSIT, 300, null, null);
		assertEquals(300, player.getCoins());
	}

	@Test
	public void testPlayerActWithdraw() {
		player.deposit(300);
		player.act(PlayerActionType.WITHDRAW, 200, null, null);
		assertEquals(100, player.getCoins());
	}

	@Test
	public void testPlayerActBet() {
		Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchData);
		player.deposit(50);
		player.act(PlayerActionType.BET, 30, match, BettingSide.A);
		assertEquals(20, player.getCoins());
	}
}
