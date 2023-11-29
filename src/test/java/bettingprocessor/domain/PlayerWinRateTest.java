package bettingprocessor.domain;

import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.domain.objects.Player;
import bettingprocessor.dto.MatchData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bettingprocessor.util.objectgenerator.MatchGenerator;
import bettingprocessor.util.objectgenerator.PlayerGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerWinRateTest {
	private Player player;
	private Casino casino;
	private static MatchData sampleMatchDataWithASideWinning;
	private static MatchData sampleMatchDataWithBSideWinning;
	private static MatchData sampleMatchDataWithDraw;

	@BeforeEach
	public void createPlayerAndCasino() {
		casino = new Casino();
		player = PlayerGenerator.generatePlayerWithRandomID(casino);
	}

	@BeforeAll
	public static void createSampleMatchData() {
		sampleMatchDataWithASideWinning = new MatchData(1.00, 0.75, MatchOutcome.A);
		sampleMatchDataWithBSideWinning = new MatchData(1.00, 0.75, MatchOutcome.B);
		sampleMatchDataWithDraw = new MatchData(1.00, 0.75, MatchOutcome.DRAW);
	}

	@Test
	public void testPlayerBettingWinRateAllWins() {
		Match matchOne = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
		Match matchTwo = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
		Match matchThree = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
		casino.addPlayer(player);
		casino.addMatch(matchOne);
		casino.addMatch(matchTwo);
		casino.addMatch(matchThree);

		player.deposit(500);
		player.betOnMatch(100, matchOne, BettingSide.A);
		player.betOnMatch(20, matchTwo, BettingSide.B);
		player.betOnMatch(200, matchThree, BettingSide.A);

		casino.playMatches();

		assertEquals(1, player.getWinRate());
	}

	@Test
	public void testPlayerBettingWinRateAllLosses() {
		Match matchOne = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
		Match matchTwo = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
		Match matchThree = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
		casino.addPlayer(player);
		casino.addMatch(matchOne);
		casino.addMatch(matchTwo);
		casino.addMatch(matchThree);

		player.deposit(500);
		player.betOnMatch(100, matchOne, BettingSide.B);
		player.betOnMatch(20, matchTwo, BettingSide.A);
		player.betOnMatch(200, matchThree, BettingSide.B);

		casino.playMatches();

		assertEquals(0, player.getWinRate());
	}

	@Test
	public void testPlayerBettingWinRateRandom() {
		Match matchOne = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
		Match matchTwo = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
		Match matchThree = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithDraw, casino);
		Match matchFour = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
		casino.addPlayer(player);
		casino.addMatch(matchOne);
		casino.addMatch(matchTwo);
		casino.addMatch(matchThree);
		casino.addMatch(matchFour);

		player.deposit(500);
		player.betOnMatch(100, matchOne, BettingSide.B);
		player.betOnMatch(20, matchTwo, BettingSide.A);
		player.betOnMatch(200, matchThree, BettingSide.B);
		player.betOnMatch(50, matchFour, BettingSide.B);

		casino.playMatches();

		assertEquals(0.5, player.getWinRate());
	}
}
