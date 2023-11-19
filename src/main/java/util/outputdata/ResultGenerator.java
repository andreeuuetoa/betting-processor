package util.outputdata;

import dto.PlayerAction;
import dto.ResultLegitimatePlayer;

public class ResultGenerator {
	public String generateLegitimatePlayerResult(ResultLegitimatePlayer legitimatePlayer) {
		return String.format("%s %d %.2f",
				legitimatePlayer.getPlayerId(),
				legitimatePlayer.getFinalBalance(),
				legitimatePlayer.getBettingWinRate());
	}

	public String generateIllegitimatePlayerResult(PlayerAction illegitimatePlayerAction) {
		return String.format("%s %s %s %d %s",
				illegitimatePlayerAction.getPlayerId(),
				illegitimatePlayerAction.getType(),
				illegitimatePlayerAction.getMatchId(),
				illegitimatePlayerAction.getAmount(),
				illegitimatePlayerAction.getBettingSide());
	}
}
