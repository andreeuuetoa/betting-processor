package util.inputdata;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlayerActionDataFileReader {

	public List<String> getPlayerActionsAsStringsFromFileInPath(Path path) {
		List<String> playerActions = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				playerActions.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error reading file:" + e.getMessage());
		}
		return playerActions;
	}
}
