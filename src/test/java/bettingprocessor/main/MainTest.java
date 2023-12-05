package bettingprocessor.main;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
	@Test
	public void testMatchDataFilePathIsCorrect() {
		assertEquals(Paths.get("src", "main", "resources", "match_data.txt"), Main.matchDataPath);
	}

	@Test
	public void testMatchDataFileExists() {
		assertDoesNotThrow(() -> {
			Files.newBufferedReader(Main.matchDataPath);
		});
	}

	@Test
	public void testPlayerDataFilePathIsCorrect() {
		assertEquals(Paths.get("src", "main", "resources", "player_data.txt"), Main.playerDataPath);
	}

	@Test
	public void testPlayerDataFileExists() {
		assertDoesNotThrow(() -> {
			Files.newBufferedReader(Main.playerDataPath);
		});
	}

	@Test
	public void testResultsFilePathIsCorrect() {
		assertEquals(Paths.get("src", "main", "java", "bettingprocessor", "main", "results.txt"), Main.resultPath);
	}

	@Test
	public void testAfterRunningMainResultsFileExists() {
		Main.main(null);
		assertDoesNotThrow(() -> {
			Files.newBufferedReader(Main.resultPath);
		});
	}
}
