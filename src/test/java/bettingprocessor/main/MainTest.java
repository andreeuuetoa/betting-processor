package bettingprocessor.main;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
	@Test
	public void testMatchDataFilePathIsCorrect() {
		assertEquals(Paths.get("src", "main", "resources", "match_data.txt"), Main.matchDataPath);
	}

	@Test
	public void testMatchDataFileExists() {
		assertTrue(new File(String.valueOf(Main.matchDataPath)).isFile());
	}

	@Test
	public void testPlayerDataFilePathIsCorrect() {
		assertEquals(Paths.get("src", "main", "resources", "player_data.txt"), Main.playerDataPath);
	}

	@Test
	public void testPlayerDataFileExists() {
		assertTrue(new File(String.valueOf(Main.playerDataPath)).isFile());
	}

	@Test
	public void testResultsFilePathIsCorrect() {
		assertEquals(Paths.get("src", "main", "java", "bettingprocessor", "main", "results.txt"), Main.resultPath);
	}

	@Test
	public void testAfterRunningMainResultsFileExists() {
		Main.main(null);
		assertTrue(new File(String.valueOf(Main.resultPath)).isFile());
	}
}
