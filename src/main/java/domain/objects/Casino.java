package domain.objects;

import lombok.Getter;

import java.util.*;

@Getter
public class Casino {
    private long balance;
    private List<Match> matches;
    private Set<Player> illegalPlayers;

    public Casino() {
        balance = 0;
        matches = new ArrayList<>();
        illegalPlayers = new HashSet<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public void playMatches() {
        for (Match match : matches) {
            balance += match.calculateCasinoProfit();
            match.payToPlayers();
        }
    }
}
