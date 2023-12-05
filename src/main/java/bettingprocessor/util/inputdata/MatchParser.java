package bettingprocessor.util.inputdata;

import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.dto.MatchData;
import bettingprocessor.dto.MatchInfo;

import java.util.UUID;

public class MatchParser {

	public MatchInfo parseMatchInfoFromString(String line) {
		String[] matchElements = line.split(",");

		UUID matchId = UUID.fromString(matchElements[0]);
		double aBetRate = Double.parseDouble(matchElements[1]);
		double bBetRate = Double.parseDouble(matchElements[2]);
		MatchOutcome matchOutcome = parseMatchOutcomeFromString(matchElements[3]);

		return new MatchInfo(matchId, new MatchData(aBetRate, bBetRate, matchOutcome));
	}

	private MatchOutcome parseMatchOutcomeFromString(String matchOutcomeAsString) {
		return switch (matchOutcomeAsString) {
			case "A" -> MatchOutcome.A;
			case "B" -> MatchOutcome.B;
			case "DRAW" -> MatchOutcome.DRAW;
			default -> throw new IllegalStateException("Unexpected match outcome: " + matchOutcomeAsString);
		};
	}
}
