package util.files;

import domain.constants.BettingSide;
import domain.constants.PlayerActionType;
import dto.PlayerAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerActionParserTest {
	@Test
	public void testCreateDepositPlayerActionFromPlayerData() {
		String playerActionAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,DEPOSIT,,4000,";
		PlayerAction playerAction = new PlayerActionParser().parsePlayerActionFromString(playerActionAsString);
		assertDepositPlayerActionWasCreatedCorrectly(playerAction);
	}

	private void assertDepositPlayerActionWasCreatedCorrectly(PlayerAction playerAction) {
		assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4", playerAction.getPlayerId().toString());
		assertEquals(PlayerActionType.DEPOSIT, playerAction.getType());
		assertNull(playerAction.getMatchId());
		assertEquals(4000, playerAction.getAmount());
		assertNull(playerAction.getBettingSide());
	}

	@Test
	public void testCreateWithdrawPlayerActionFromPlayerData() {
		String playerActionAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,";
		PlayerAction playerAction = new PlayerActionParser().parsePlayerActionFromString(playerActionAsString);
		assertWithdrawPlayerActionWasCreatedCorrectly(playerAction);
	}

	private void assertWithdrawPlayerActionWasCreatedCorrectly(PlayerAction playerAction) {
		assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4", playerAction.getPlayerId().toString());
		assertEquals(PlayerActionType.WITHDRAW, playerAction.getType());
		assertNull(playerAction.getMatchId());
		assertEquals(200, playerAction.getAmount());
		assertNull(playerAction.getBettingSide());
	}

	@Test
	public void testCreateBetPlayerActionFromPlayerData() {
		String playerActionAsString = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,abae2255-4255-4304-8589-737cdff61640,500,A";
		PlayerAction playerAction = new PlayerActionParser().parsePlayerActionFromString(playerActionAsString);
		assertBetPlayerActionWasCreatedCorrectly(playerAction);
	}

	private void assertBetPlayerActionWasCreatedCorrectly(PlayerAction playerAction) {
		assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4", playerAction.getPlayerId().toString());
		assertEquals(PlayerActionType.BET, playerAction.getType());
		assertEquals("abae2255-4255-4304-8589-737cdff61640", playerAction.getMatchId().toString());
		assertEquals(500, playerAction.getAmount());
		assertEquals(BettingSide.A, playerAction.getBettingSide());
	}
}
