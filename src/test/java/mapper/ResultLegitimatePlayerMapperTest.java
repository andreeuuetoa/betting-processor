package mapper;

import domain.objects.Casino;
import domain.objects.Player;
import dto.ResultLegitimatePlayer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultLegitimatePlayerMapperTest {
	@Test
	public void testMapOneLegitimatePlayer() {
		UUID playerId = UUID.randomUUID();
		Player player = new Player(playerId, new Casino());
		player.increaseMatchesPlayedByOne();
		player.increaseMatchesPlayedByOne();
		player.increaseMatchesWonByOne();
		player.deposit(240);
		List<Player> players = List.of(player);
		List<ResultLegitimatePlayer> resultLegitimatePlayers = new ResultLegitimatePlayerMapper().getResultLegitimatePlayers(players);
		assertEquals(1, resultLegitimatePlayers.size());
		ResultLegitimatePlayer playerMapped = resultLegitimatePlayers.get(0);
		assertEquals(playerId, playerMapped.getPlayerId());
		assertEquals(240, playerMapped.getFinalBalance());
		assertEquals(0.5, playerMapped.getBettingWinRate());
	}

	@Test
	public void testMapMultipleLegitimatePlayers() {
		List<Player> players = List.of(
				new Player(UUID.randomUUID(), new Casino()),
				new Player(UUID.randomUUID(), new Casino()),
				new Player(UUID.randomUUID(), new Casino())
		);
		List<ResultLegitimatePlayer> resultLegitimatePlayers = new ResultLegitimatePlayerMapper().getResultLegitimatePlayers(players);
		assertEquals(3, resultLegitimatePlayers.size());
	}
}
