package util.outputdata;

import dto.PlayerAction;
import dto.ResultLegitimatePlayer;

import java.util.List;

public class ResultGenerator {
	public String generateLegitimatePlayerResult(ResultLegitimatePlayer legitimatePlayer) {
		return String.format("%s %d %.2f",
				legitimatePlayer.getPlayerId(),
				legitimatePlayer.getFinalBalance(),
				legitimatePlayer.getBettingWinRate());
	}

	public String generateIllegitimatePlayerActionResult(PlayerAction illegitimatePlayerAction) {
		return String.format("%s %s %s %d %s",
				illegitimatePlayerAction.getPlayerId(),
				illegitimatePlayerAction.getType(),
				illegitimatePlayerAction.getMatchId(),
				illegitimatePlayerAction.getAmount(),
				illegitimatePlayerAction.getBettingSide());
	}

	public String generateLegitimatePlayersResult(List<ResultLegitimatePlayer> legitimatePlayers) {
		int playerCount = 0;
		StringBuilder legitimatePlayersStringBuilder = new StringBuilder();
		for (ResultLegitimatePlayer legitimatePlayer : legitimatePlayers) {
			legitimatePlayersStringBuilder.append(generateLegitimatePlayerResult(legitimatePlayer));
			if (isLastPlayerInList(legitimatePlayers, playerCount)) {
				legitimatePlayersStringBuilder.append("\n");
			}
			playerCount++;
		}
		return legitimatePlayersStringBuilder.toString();
	}

	private boolean isLastPlayerInList(List<ResultLegitimatePlayer> legitimatePlayers, int playerCount) {
		return playerCount < legitimatePlayers.size() - 1;
	}

	public String generateIllegitimatePlayerActionsResult(List<PlayerAction> illegitimatePlayerActions) {
		int playerActionCount = 0;
		StringBuilder illegitimatePlayerActionsStringBuilder = new StringBuilder();
		for (PlayerAction illegitimatePlayerAction : illegitimatePlayerActions) {
			illegitimatePlayerActionsStringBuilder.append(generateIllegitimatePlayerActionResult(illegitimatePlayerAction));
			if (isLastPlayerActionInList(illegitimatePlayerActions, playerActionCount)) {
				illegitimatePlayerActionsStringBuilder.append("\n");
			}
			playerActionCount++;
		}
		return illegitimatePlayerActionsStringBuilder.toString();
	}

	private boolean isLastPlayerActionInList(List<PlayerAction> illegitimatePlayerActions, int playerActionCount) {
		return playerActionCount < illegitimatePlayerActions.size() - 1;
	}
}
