package bettingprocessor.mapper;

import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.dto.MatchData;
import bettingprocessor.dto.MatchInfo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchMapperTest {
	@Test
	public void testMapOneMatchInfoToMatchWithCasino() {
		UUID matchId = UUID.randomUUID();
		List<MatchInfo> matchInfos = List.of(
				new MatchInfo(matchId, new MatchData(1.00, 0.75, MatchOutcome.A))
		);
		Casino casino = new Casino();
		List<Match> matchesWithCasino = new MatchMapper().getMatchesWithCasino(matchInfos, casino);
		assertEquals(1, matchesWithCasino.size());
		Match mappedMatch = matchesWithCasino.get(0);
		assertEquals(matchId, mappedMatch.getId());
		assertEquals(1.00, mappedMatch.getMatchData().getABetRate());
		assertEquals(0.75, mappedMatch.getMatchData().getBBetRate());
		assertEquals(MatchOutcome.A, mappedMatch.getMatchData().getMatchOutcome());
		assertEquals(casino, mappedMatch.getCasino());
	}

	@Test
	public void testMapManyMatchInfosToMatchesWithCasino() {
		List<MatchInfo> matchInfos = List.of(
				new MatchInfo(UUID.randomUUID(), new MatchData(1.00, 0.75, MatchOutcome.A)),
				new MatchInfo(UUID.randomUUID(), new MatchData(1.25, 0.85, MatchOutcome.B)),
				new MatchInfo(UUID.randomUUID(), new MatchData(1.1, 0.5, MatchOutcome.A))
		);
		Casino casino = new Casino();
		List<Match> matchesWithCasino = new MatchMapper().getMatchesWithCasino(matchInfos, casino);
		assertEquals(3, matchesWithCasino.size());
	}
}
