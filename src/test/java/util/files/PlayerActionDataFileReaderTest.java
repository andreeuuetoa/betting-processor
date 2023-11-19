package util.files;

import domain.constants.BettingSide;
import domain.constants.PlayerActionType;
import dto.PlayerAction;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerActionDataFileReaderTest {
    @Test
    public void testDepositPlayerActionDataFileExistsAndContainsData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_deposit_data.txt");
        String oneMatchData = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,DEPOSIT,,4000,";
        assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            assertEquals(oneMatchData, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@Test
	public void testReadFromPlayerDataWithOneRow() {
		Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_withdraw_data.txt");
		List<String> playerActionsAsStrings = new PlayerActionDataFileReader().getPlayerActionsAsStringsFromFileInPath(path);
		assertEquals(1, playerActionsAsStrings.size());
		String matchAsString = playerActionsAsStrings.get(0);
		assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,", matchAsString);
	}

	@Test
	public void testReadFromPlayerDataWithMultipleRows() {
		Path path = Paths.get("src", "test", "resources", "playerdata", "player_data.txt");
		List<String> matches = new MatchDataFileReader().getMatchesAsStringsFromFileInPath(path);
		assertEquals(19, matches.size());
	}
}
