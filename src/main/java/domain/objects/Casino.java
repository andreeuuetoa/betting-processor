package domain.objects;

import dto.PlayerAction;
import lombok.Getter;

import java.util.*;

@Getter
public class Casino {
    private long balance;
    private final List<Match> matches;
    private final List<Player> illegitimatePlayers;
	private final List<PlayerAction> illegitimatePlayersFirstActions;
	private final Set<Player> players;

    public Casino() {
        balance = 0;
        matches = new ArrayList<>();
        illegitimatePlayers = new ArrayList<>();
		illegitimatePlayersFirstActions = new ArrayList<>();
		players = new HashSet<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

	public void addIllegitimatePlayer(Player player) {
		illegitimatePlayers.add(player);
		illegitimatePlayers.sort(Comparator.comparing(Player::getId));
	}

	public void addIllegitimatePlayerAction(PlayerAction playerAction) {
		illegitimatePlayersFirstActions.add(playerAction);
	}

	public Match getMatchById(UUID matchId) {
		List<Match> matchesWithId = matches.stream().filter(x -> x.getId().equals(matchId)).toList();
		if (matchesWithId.isEmpty()) {
			return null;
		}
		if (matchesWithId.size() > 1) {
			throw new RuntimeException("Found match with duplicate ID: " + matchId);
		}
		return matchesWithId.get(0);
	}

    public void playMatches() {
        for (Match match : matches) {
            balance += match.calculateCasinoProfit();
            match.payToPlayers();
			match.updateGamesPlayedAndWonForPlayers();
        }
    }

	public Player getPlayerById(UUID playerId) {
		List<Player> playersWithID = players.stream().filter(x -> x.getId().equals(playerId)).toList();
		if (playersWithID.isEmpty()) {
			return null;
		}
		if (playersWithID.size() > 1) {
			throw new RuntimeException("Found player with duplicate ID: " + playerId);
		}
		return playersWithID.get(0);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public List<Player> getLegitimatePlayers() {
		return players.stream().filter(x -> !illegitimatePlayers.contains(x)).toList();
	}
}
