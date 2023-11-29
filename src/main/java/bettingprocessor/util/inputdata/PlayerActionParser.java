package bettingprocessor.util.inputdata;

import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.PlayerActionType;
import bettingprocessor.dto.PlayerAction;

import java.util.UUID;

public class PlayerActionParser {
	public PlayerAction parsePlayerActionFromString(String playerActionAsString) {
		String[] playerActionElements = playerActionAsString.split(",");
		UUID playerId = UUID.fromString(playerActionElements[0]);
		PlayerActionType playerActionType = parsePlayerActionTypeFromString(playerActionElements[1]);
		UUID matchId = playerActionElements[2].isEmpty() ? null : UUID.fromString(playerActionElements[2]);
		int amount = Integer.parseInt(playerActionElements[3]);
		BettingSide bettingSide = playerActionElements.length == 5 ? parseBettingSideFromString(playerActionElements[4]) : null;
		return new PlayerAction(playerId, playerActionType, matchId, amount, bettingSide);
	}

	private PlayerActionType parsePlayerActionTypeFromString(String playerActionTypeAsString) {
		return switch (playerActionTypeAsString) {
			case "DEPOSIT" -> PlayerActionType.DEPOSIT;
			case "WITHDRAW" -> PlayerActionType.WITHDRAW;
			case "BET" -> PlayerActionType.BET;
			default -> throw new IllegalStateException("Unexpected match outcome: " + playerActionTypeAsString);
		};
	}

	private BettingSide parseBettingSideFromString(String bettingSideAsString) {
		return switch (bettingSideAsString) {
			case "A" -> BettingSide.A;
			case "B" -> BettingSide.B;
			default -> throw new IllegalStateException("Unexpected match outcome: " + bettingSideAsString);
		};
	}
}
