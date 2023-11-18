package domain.objects;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Casino {
    private long balance;
    private List<Match> matches;

    public Casino() {
        balance = 0;
        matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public void playMatches() {
        for (Match match : matches) {
            int casinoProfitOnMatch = match.calculateCasinoProfit();
            balance += casinoProfitOnMatch;
            match.payToPlayers();
        }
    }
}
