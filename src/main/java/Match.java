import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Match {
    private final double aBetRate;
    private final double bBetRate;
    private final char winningSide;
    private List<Betting> bettings;

    public Match(double aBetRate, double bBetRate, char winningSide) {
        this.aBetRate = aBetRate;
        this.bBetRate = bBetRate;
        this.winningSide = winningSide;
        bettings = new ArrayList<>();
    }

    public void addBetting(Betting betting) {
        if (bettingsCointainsABettingMadeByPlayer(betting.getPlayer())) {
            throw new RuntimeException("This player is already betting on this match!");
        }
        bettings.add(betting);
    }

    private boolean bettingsCointainsABettingMadeByPlayer(Player player) {
        return bettings.stream().map(Betting::getPlayer).toList().contains(player);
    }
}
