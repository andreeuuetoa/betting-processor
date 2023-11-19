package util.inputdata;

import dto.MatchInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MatchDataFileExtractor {
	public List<MatchInfo> extractMatchInfoFromFileInPath(Path path) {
		List<MatchInfo> matchInfos = new ArrayList<>();
		List<String> matchesAsStrings = new MatchDataFileReader().getMatchesAsStringsFromFileInPath(path);
		MatchParser matchParser = new MatchParser();
		for (String matchAsString : matchesAsStrings) {
			MatchInfo parsedMatchInfo = matchParser.parseMatchInfoFromString(matchAsString);
			matchInfos.add(parsedMatchInfo);
		}
		return matchInfos;
	}
}
