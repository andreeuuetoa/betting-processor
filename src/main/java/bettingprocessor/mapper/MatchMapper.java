package bettingprocessor.mapper;

import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.dto.MatchInfo;

import java.util.ArrayList;
import java.util.List;

public class MatchMapper {
	public List<Match> getMatchesWithCasino(List<MatchInfo> matchesFromFile, Casino casino) {
		List<Match> newMatchesWithCasino = new ArrayList<>();
		for (MatchInfo matchInfo : matchesFromFile) {
			Match newMatch = new Match(matchInfo.getId(), matchInfo.getMatchData(), casino);
			newMatchesWithCasino.add(newMatch);
		}
		return newMatchesWithCasino;
	}
}
