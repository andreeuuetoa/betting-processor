package util.inputdata;

import dto.PlayerAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlayerActionDataFileExtractor {
	public List<PlayerAction> extractPlayerActionInfoFromFileInPath(Path path) {
		List<PlayerAction> playerActionInfos = new ArrayList<>();
		List<String> playerActionsAsStrings = new PlayerActionDataFileReader(path).getPlayerActionsAsStringsFromFile();
		PlayerActionParser playerActionParser = new PlayerActionParser();
		for (String playerActionAsString : playerActionsAsStrings) {
			PlayerAction parsedMatchInfo = playerActionParser.parsePlayerActionFromString(playerActionAsString);
			playerActionInfos.add(parsedMatchInfo);
		}
		return playerActionInfos;
	}
}
