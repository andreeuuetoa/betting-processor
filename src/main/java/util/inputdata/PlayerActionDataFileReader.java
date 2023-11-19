package util.inputdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlayerActionDataFileReader {
	private Path playerActionDataFilePath;

	public PlayerActionDataFileReader(Path path) {
		playerActionDataFilePath = path;
	}

	public List<String> getPlayerActionsAsStringsFromFile() {
		List<String> playerActions = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(playerActionDataFilePath)) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				playerActions.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException("File in path: " + playerActionDataFilePath.toString() + " was not found.");
		}
		return playerActions;
	}
}
