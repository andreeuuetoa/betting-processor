package bettingprocessor.util.outputdata;

import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.PlayerActionType;
import bettingprocessor.dto.PlayerAction;
import bettingprocessor.dto.ResultLegitimatePlayer;
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

	@Test
	public void testMultipleIllegitimatePlayerActionsToString() {
		String expectedIllegitimatePlayerActionsAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 BET abae2255-4255-4304-8589-737cdff61640 5000 A\n4925ac98-833b-454b-9342-13ed3dfd3ccf WITHDRAW null 8093 null\n5de45517-4312-4596-9702-9277b6d649f2 BET de0785e2-58cb-413c-8e4b-1d135857733b 300 B";
		List<PlayerAction> threeIllegitimatePlayerActions = generateThreeSampleIllegitimatePlayerActions();
		String actualLegitimatePlayersAsString = new ResultGenerator().generateIllegitimatePlayerActionsResult(threeIllegitimatePlayerActions);
		assertEquals(expectedIllegitimatePlayerActionsAsString, actualLegitimatePlayersAsString);
	}

	private List<PlayerAction> generateThreeSampleIllegitimatePlayerActions() {
		return List.of(
				new PlayerAction(UUID.fromString("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4"), PlayerActionType.BET, UUID.fromString("abae2255-4255-4304-8589-737cdff61640"), 5000, BettingSide.A),
				new PlayerAction(UUID.fromString("4925ac98-833b-454b-9342-13ed3dfd3ccf"), PlayerActionType.WITHDRAW, null, 8093, null),
				new PlayerAction(UUID.fromString("5de45517-4312-4596-9702-9277b6d649f2"), PlayerActionType.BET, UUID.fromString("de0785e2-58cb-413c-8e4b-1d135857733b"), 300, BettingSide.B)
		);
	}

	@Test
	public void testGenerateFullResultAsString() {
		String expectedResultString = """
				163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 4321 0,80
				abae2255-4255-4304-8589-737cdff61640 5000 0,95
				5de45517-4312-4596-9702-9277b6d649f2 918 0,45
				
				163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 BET abae2255-4255-4304-8589-737cdff61640 5000 A
				4925ac98-833b-454b-9342-13ed3dfd3ccf WITHDRAW null 8093 null
				5de45517-4312-4596-9702-9277b6d649f2 BET de0785e2-58cb-413c-8e4b-1d135857733b 300 B
				
				1523""";
		List<ResultLegitimatePlayer> threeLegitimatePlayers = generateThreeSampleLegitimatePlayers();
		List<PlayerAction> threeIllegitimatePlayerActions = generateThreeSampleIllegitimatePlayerActions();
		long casinoBalance = 1523;
		String actualResultString = new ResultGenerator().generateCompleteOutputResult(threeLegitimatePlayers, threeIllegitimatePlayerActions, casinoBalance);
		assertEquals(expectedResultString, actualResultString);
	}
}
