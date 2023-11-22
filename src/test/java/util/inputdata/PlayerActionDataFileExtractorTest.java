package util.inputdata;

import dto.PlayerAction;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerActionDataFileExtractorTest {
	@Test
	public void testExtractMatchFromFileWithOneMatch() {
		Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_deposit_data.txt");
		List<PlayerAction> playerActionsFromFile = new PlayerActionDataFileExtractor(path).getPlayerActions();
		assertEquals(1, playerActionsFromFile.size());
	}

	@Test
	public void testExtractMatchesFromFileWithMultipleMatches() {
		Path path = Paths.get("src", "test", "resources", "playerdata", "player_data.txt");
		List<PlayerAction> playerActionsFromFile = new PlayerActionDataFileExtractor(path).getPlayerActions();
		assertEquals(19, playerActionsFromFile.size());
	}
}
