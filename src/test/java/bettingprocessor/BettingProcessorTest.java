package bettingprocessor;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class BettingProcessorTest {
	@Test
	public void testBettingProcessorHappyPath() {
		Path matchDataPath = Paths.get("src", "test", "resources", "matchdata", "match_data.txt");
		Path playerDataPath = Paths.get("src", "test", "resources", "playerdata", "player_data.txt");
		Path resultPath = Paths.get("src", "test", "resources", "result", "results.txt");
		BettingProcessor bettingProcessor = new BettingProcessor(matchDataPath, playerDataPath, resultPath);
		bettingProcessor.processBettingData();
		assertDoesNotThrow(() -> Files.newBufferedReader(resultPath));
	}
}
