package bettingprocessor.util.inputdata;

import bettingprocessor.dto.MatchInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MatchDataFileExtractor implements MatchDataExtractor {
	private final Path path;

	public MatchDataFileExtractor(Path path) {
		this.path = path;
	}

	public List<MatchInfo> getMatchInfo() {
		List<MatchInfo> matchInfos = new ArrayList<>();
		List<String> matchesAsStrings = new MatchDataFileReader(path).getMatchesAsStringsFromFile();
		MatchParser matchParser = new MatchParser();
		for (String matchAsString : matchesAsStrings) {
			MatchInfo parsedMatchInfo = matchParser.parseMatchInfoFromString(matchAsString);
			matchInfos.add(parsedMatchInfo);
		}
		return matchInfos;
	}
}
