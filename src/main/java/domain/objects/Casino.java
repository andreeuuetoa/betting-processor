package domain.objects;

import lombok.Getter;

import java.util.*;

@Getter
public class Casino {
    private long balance;
    private List<Match> matches;
    private Set<Player> illegitimatePlayers;

    public Casino() {
        balance = 0;
        matches = new ArrayList<>();
        illegitimatePlayers = new HashSet<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

	public void addIllegitimatePlayer(Player player) {
		illegitimatePlayers.add(player);
	}

	public Match getMatchByID(UUID matchId) {
		List<Match> matchesWithID = matches.stream().filter(x -> x.getId().equals(matchId)).toList();
		if (matchesWithID.isEmpty()) {
			throw new RuntimeException(String.format("No match with ID %s was found!", matchId));
		}
		if (matchesWithID.size() > 1) {
			throw new RuntimeException(String.format("Multiple matches with ID %s were found!", matchId));
		}
		return matches.stream().filter(x -> x.getId().equals(matchId)).toList().get(0);
	}

    public void playMatches() {
        for (Match match : matches) {
            balance += match.calculateCasinoProfit();
            match.payToPlayers();
        }
    }
}
