import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Match {
    private MatchData matchData;
    private List<Betting> bettings;

    public Match(MatchData matchData) {
        this.matchData = matchData;
        bettings = new ArrayList<>();
    }

    public void addBetting(Betting betting) {
        if (bettingsContainsABettingMadeByPlayer(betting.getPlayer())) {
            throw new RuntimeException("This player is already betting on this match!");
        }
        bettings.add(betting);
    }

    private boolean bettingsContainsABettingMadeByPlayer(Player player) {
        return bettings.stream().map(Betting::getPlayer).toList().contains(player);
    }

    public void payToPlayers() {
        for (Betting betting : bettings) {
            new BettingProcessor(getMatchData()).processBetting(betting);
        }
    }
}
