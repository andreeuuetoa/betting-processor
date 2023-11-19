package main;

import bettingprocessor.BettingProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
	    Path matchDataPath = Paths.get("src", "main", "resources", "match_data.txt");
		Path playerDataPath = Paths.get("src", "main", "resources", "player_data.txt");
		Path resultPath = Paths.get("src", "main", "java", "main", "results.txt");
	    BettingProcessor bettingProcessor = new BettingProcessor(matchDataPath, playerDataPath, resultPath);
		bettingProcessor.processBettingData();
    }
}
