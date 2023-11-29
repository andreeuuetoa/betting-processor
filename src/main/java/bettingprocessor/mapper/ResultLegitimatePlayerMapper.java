package bettingprocessor.mapper;

import bettingprocessor.domain.objects.Player;
import bettingprocessor.dto.ResultLegitimatePlayer;

import java.util.ArrayList;
import java.util.List;

public class ResultLegitimatePlayerMapper {
	public List<ResultLegitimatePlayer> getResultLegitimatePlayers(List<Player> players) {
		List<ResultLegitimatePlayer> resultLegitimatePlayers = new ArrayList<>();
		for (Player player : players) {
			ResultLegitimatePlayer newResultLegitimatePlayer = new ResultLegitimatePlayer(player.getId(), player.getCoins(), player.getWinRate());
			resultLegitimatePlayers.add(newResultLegitimatePlayer);
		}
		return resultLegitimatePlayers;
	}
}
