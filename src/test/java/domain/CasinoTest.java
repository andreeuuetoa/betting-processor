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
import util.objectgenerator.MatchGenerator;
import util.objectgenerator.PlayerGenerator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CasinoTest {
    private static MatchData sampleMatchDataWithASideWinning;
    private static MatchData sampleMatchDataWithBSideWinning;
    private static MatchData sampleMatchDataWithDraw;

    private Casino casino;
    private Player player;

    @BeforeEach
    public void createNewCasino() {
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
    public void testCasinoInitialBalanceIsZero() {
        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testCasinoCanHostMatches() {
        Match matchOne = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        Match matchTwo = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
        Match matchThree = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithDraw, casino);

        casino.addMatch(matchOne);
        casino.addMatch(matchTwo);
        casino.addMatch(matchThree);

        assertEquals(3, casino.getMatches().size());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndPlayerGetsMoney() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

		casino.addPlayer(player);
        casino.addMatch(match);
        casino.playMatches();

        assertEquals(115, player.getCoins());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoLosesMoney() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

		casino.addPlayer(player);
        casino.addMatch(match);
        casino.playMatches();

        assertEquals(-15, casino.getBalance());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoGetsAProfit() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithBSideWinning, casino);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

		casino.addPlayer(player);
        casino.addMatch(match);
        casino.playMatches();

        assertEquals(50, casino.getBalance());
    }

    @Test
    public void testCasinoPlaysOneMatchWithOnePlayerAndCasinoBalanceStaysConstant() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithDraw, casino);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);

		casino.addPlayer(player);
        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testIllegalMoveByPlayerDoesNotImpactCasinoBalance() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        player.deposit(100);
        player.betOnMatch(150, match, BettingSide.A);

		casino.addPlayer(player);
        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }

    @Test
    public void testLegalMoveAndThenAnIllegalMoveDoesNotImpactCasinoBalance() {
        Match match = MatchGenerator.generateMatchWithRandomID(sampleMatchDataWithASideWinning, casino);
        player.deposit(100);
        player.betOnMatch(50, match, BettingSide.A);
        player.betOnMatch(100, match, BettingSide.A);

		casino.addPlayer(player);
        casino.addMatch(match);
        casino.playMatches();

        assertEquals(0, casino.getBalance());
    }

	@Test
	public void testGetMatchByIdFromCasino() {
		UUID matchId = UUID.randomUUID();
		Casino casino = new Casino();

		casino.addMatch(new Match(matchId, sampleMatchDataWithDraw, casino));
		Match matchFound = casino.getMatchById(matchId);

		assertEquals(matchId, matchFound.getId());
	}

	@Test
	public void testGettingMatchThatIsNotInCasinoByIdFromCasinoThrowsException() {
		Casino casino = new Casino();

		Match matchFound = casino.getMatchById(UUID.randomUUID());

		assertNull(matchFound);
	}

	@Test
	public void testGettingMatchByIdFromCasinoWithMatchesWithDuplicateIdsThrowsException() {
		UUID matchId = UUID.randomUUID();
		Casino casino = new Casino();

		casino.addMatch(new Match(matchId, sampleMatchDataWithASideWinning, casino));
		casino.addMatch(new Match(matchId, sampleMatchDataWithBSideWinning, casino));
		assertThrows(RuntimeException.class, () -> {
			casino.getMatchById(matchId);
		});
	}

	@Test
	public void testGetPlayerByIdFromCasino() {
		UUID playerId = UUID.randomUUID();
		Casino casino = new Casino();

		casino.addPlayer(new Player(playerId, casino));
		Player playerFound = casino.getPlayerById(playerId);

		assertEquals(playerId, playerFound.getId());
	}

	@Test
	public void testGettingPlayerThatIsNotInCasinoByIdFromCasinoReturnsNull() {
		Casino casino = new Casino();

		Player playerFound = casino.getPlayerById(UUID.randomUUID());

		assertNull(playerFound);
	}

	@Test
	public void testGettingPlayerByIdFromCasinoWithMatchesWithDuplicateIdsThrowsException() {
		UUID playerId = UUID.randomUUID();
		Casino casino = new Casino();

		casino.addPlayer(new Player(playerId, casino));
		casino.addPlayer(new Player(playerId, casino));
		assertThrows(RuntimeException.class, () -> {
			casino.getPlayerById(playerId);
		});
	}

	@Test
	public void testGetLegitimatePlayersFromCasino() {
		Casino casino = new Casino();
		Player playerOne = new Player(UUID.randomUUID(), casino);
		Player playerTwo = new Player(UUID.randomUUID(), casino);
		Player playerThree = new Player(UUID.randomUUID(), casino);
		Player playerFour = new Player(UUID.randomUUID(), casino);
		Player playerFive = new Player(UUID.randomUUID(), casino);

		casino.addPlayer(playerOne);
		casino.addPlayer(playerTwo);
		casino.addPlayer(playerThree);
		casino.addPlayer(playerFour);
		casino.addPlayer(playerFive);
		casino.addIllegitimatePlayer(playerOne);
		casino.addIllegitimatePlayer(playerFour);

		assertEquals(3, casino.getLegitimatePlayers().size());
		assertFalse(casino.getLegitimatePlayers().contains(playerOne));
		assertTrue(casino.getLegitimatePlayers().contains(playerTwo));
		assertTrue(casino.getLegitimatePlayers().contains(playerThree));
		assertFalse(casino.getLegitimatePlayers().contains(playerFour));
		assertTrue(casino.getLegitimatePlayers().contains(playerFive));
	}
}
