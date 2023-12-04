package bettingprocessor.main;

import bettingprocessor.util.inputdata.MatchDataExtractor;
import bettingprocessor.util.inputdata.MatchDataFileExtractor;
import bettingprocessor.util.inputdata.PlayerActionDataExtractor;
import bettingprocessor.util.inputdata.PlayerActionDataFileExtractor;
import bettingprocessor.util.outputdata.ResultFileWriter;
import bettingprocessor.util.outputdata.ResultWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
	    Path matchDataPath = Paths.get("src", "main", "resources", "match_data.txt");
		Path playerDataPath = Paths.get("src", "main", "resources", "player_data.txt");
		Path resultPath = Paths.get("src", "main", "java", "bettingprocessor", "main", "results.txt");
		MatchDataExtractor matchDataExtractor = new MatchDataFileExtractor(matchDataPath);
		PlayerActionDataExtractor playerActionDataExtractor = new PlayerActionDataFileExtractor(playerDataPath);
		ResultWriter resultWriter = new ResultFileWriter(resultPath);
	    BettingProcessor bettingProcessor = new BettingProcessor(
				matchDataExtractor,
				playerActionDataExtractor,
				resultWriter
		);
		bettingProcessor.processBettingData();
    }
}
