package util.inputdata;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerActionDataFileReaderTest {
	@Test
	public void testReadFromPlayerDataWithOneRow() {
		Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_withdraw_data.txt");
		List<String> playerActionsAsStrings = new PlayerActionDataFileReader(path).getPlayerActionsAsStringsFromFile();
		assertEquals(1, playerActionsAsStrings.size());
		String matchAsString = playerActionsAsStrings.get(0);
		assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,", matchAsString);
	}

	@Test
	public void testReadFromPlayerDataWithMultipleRows() {
		Path path = Paths.get("src", "test", "resources", "playerdata", "player_data.txt");
		List<String> matches = new PlayerActionDataFileReader(path).getPlayerActionsAsStringsFromFile();
		assertEquals(19, matches.size());
	}

	@Test
	public void testReaderThrowsExceptionIfCannotFindTheFile() {
		Path path = Paths.get("incorrect-path", "player_data.txt");
		assertThrows(RuntimeException.class, () -> new PlayerActionDataFileReader(path).getPlayerActionsAsStringsFromFile());
	}
}
