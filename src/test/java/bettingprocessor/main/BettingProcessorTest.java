package bettingprocessor.main;

import org.junit.jupiter.api.Test;
import bettingprocessor.util.inputdata.MatchDataExtractor;
import bettingprocessor.util.inputdata.MatchDataFileExtractor;
import bettingprocessor.util.inputdata.PlayerActionDataExtractor;
import bettingprocessor.util.inputdata.PlayerActionDataFileExtractor;
import bettingprocessor.util.outputdata.ResultFileWriter;
import bettingprocessor.util.outputdata.ResultWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BettingProcessorTest {
	@Test
	public void testBettingProcessorHappyPath() {
		Path matchDataPath = Paths.get("src", "test", "resources", "matchdata", "match_data.txt");
		Path playerDataPath = Paths.get("src", "test", "resources", "playerdata", "player_data.txt");
		Path resultPath = Paths.get("src", "test", "resources", "result", "results.txt");
		MatchDataExtractor matchDataExtractor = new MatchDataFileExtractor(matchDataPath);
		PlayerActionDataExtractor playerActionDataExtractor = new PlayerActionDataFileExtractor(playerDataPath);
		ResultWriter resultWriter = new ResultFileWriter(resultPath);
		BettingProcessor bettingProcessor = new BettingProcessor(
				matchDataExtractor,
				playerActionDataExtractor,
				resultWriter
		);
		bettingProcessor.processBettingData();
		try (BufferedReader reader = Files.newBufferedReader(resultPath)) {
			for (int i = 0; i < 5; i++) {
				assertNotNull(reader.readLine());
			}
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
