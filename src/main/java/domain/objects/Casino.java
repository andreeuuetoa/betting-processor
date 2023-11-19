package domain.objects;

import lombok.Getter;

import java.util.*;

@Getter
public class Casino {
    private long balance;
    private List<Match> matches;
    private Set<Player> illegitimatePlayers;
	private Set<Player> players;

    public Casino() {
        balance = 0;
        matches = new ArrayList<>();
        illegitimatePlayers = new HashSet<>();
		players = new HashSet<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

	public void addIllegitimatePlayer(Player player) {
		illegitimatePlayers.add(player);
	}

	public Match getMatchById(UUID matchId) {
		List<Match> matchesWithId = matches.stream().filter(x -> x.getId().equals(matchId)).toList();
		if (matchesWithId.isEmpty()) {
			throw new RuntimeException(String.format("No match with ID %s was found!", matchId));
		}
		if (matchesWithId.size() > 1) {
			throw new RuntimeException(String.format("Multiple matches with ID %s were found!", matchId));
		}
		return matchesWithId.get(0);
	}

    public void playMatches() {
        for (Match match : matches) {
            balance += match.calculateCasinoProfit();
            match.payToPlayers();
        }
    }

	public Player getPlayerById(UUID playerId) {
		List<Player> playersWithID = players.stream().filter(x -> x.getId().equals(playerId)).toList();
		if (playersWithID.isEmpty()) {
			return null;
		}
		if (playersWithID.size() > 1) {
			return null;
		}
		return playersWithID.get(0);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}
}
