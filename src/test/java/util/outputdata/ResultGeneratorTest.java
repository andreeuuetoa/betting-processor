package util.outputdata;

import domain.constants.BettingSide;
import domain.constants.PlayerActionType;
import dto.PlayerAction;
import dto.ResultLegitimatePlayer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultGeneratorTest {
	@Test
	public void testLegitimatePlayerToString() {
		String expectedLegitimatePlayerAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 4321 0,80";
		UUID playerId = UUID.fromString("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4");
		int finalBalance = 4321;
		double bettingWinRate = 0.8;
		ResultLegitimatePlayer legitimatePlayer = new ResultLegitimatePlayer(playerId, finalBalance, bettingWinRate);
		String actualLegitimatePlayerAsString = new ResultGenerator().generateLegitimatePlayerResult(legitimatePlayer);
		assertEquals(expectedLegitimatePlayerAsString, actualLegitimatePlayerAsString);
	}

	@Test
	public void testMultipleLegitimatePlayersToString() {
		String expectedLegitimatePlayersAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 4321 0,80\nabae2255-4255-4304-8589-737cdff61640 5000 0,95\n5de45517-4312-4596-9702-9277b6d649f2 918 0,45";
		List<ResultLegitimatePlayer> threeSampleLegitimatePlayers = generateThreeSampleLegitimatePlayers();
		String actualLegitimatePlayersAsString = new ResultGenerator().generateLegitimatePlayersResult(threeSampleLegitimatePlayers);
		assertEquals(expectedLegitimatePlayersAsString, actualLegitimatePlayersAsString);
	}

	private List<ResultLegitimatePlayer> generateThreeSampleLegitimatePlayers() {
		return List.of(
				new ResultLegitimatePlayer(UUID.fromString("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4"), 4321, 0.8),
				new ResultLegitimatePlayer(UUID.fromString("abae2255-4255-4304-8589-737cdff61640"), 5000, 0.95),
				new ResultLegitimatePlayer(UUID.fromString("5de45517-4312-4596-9702-9277b6d649f2"), 918, 0.45)
		);
	}

	@Test
	public void testIllegitimatePlayerBettingToString() {
		String expectedIllegitimatePlayerAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 BET abae2255-4255-4304-8589-737cdff61640 5000 A";
		PlayerAction illegitimatePlayerAction = new PlayerAction(
				UUID.fromString("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4"),
				PlayerActionType.BET,
				UUID.fromString("abae2255-4255-4304-8589-737cdff61640"),
				5000,
				BettingSide.A
		);
		String actualIllegitimatePlayerAsString = new ResultGenerator().generateIllegitimatePlayerActionResult(illegitimatePlayerAction);
		assertEquals(expectedIllegitimatePlayerAsString, actualIllegitimatePlayerAsString);
	}

	@Test
	public void testIllegitimatePlayerWithdrawingToString() {
		String expectedIllegitimatePlayerAsString = "4925ac98-833b-454b-9342-13ed3dfd3ccf WITHDRAW null 8093 null";
		PlayerAction illegitimatePlayerAction = new PlayerAction(
				UUID.fromString("4925ac98-833b-454b-9342-13ed3dfd3ccf"),
				PlayerActionType.WITHDRAW,
				null,
				8093,
				null
		);
		String actualIllegitimatePlayerAsString = new ResultGenerator().generateIllegitimatePlayerActionResult(illegitimatePlayerAction);
		assertEquals(expectedIllegitimatePlayerAsString, actualIllegitimatePlayerAsString);
	}
}
