import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class Match {
    private final double aBetRate;
    private final double bBetRate;
    private final char winningSide;

    private Collection<Player> players;

    public Match(double aBetRate, double bBetRate, char winningSide) {
        this.aBetRate = aBetRate;
        this.bBetRate = bBetRate;
        this.winningSide = winningSide;
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if (players.contains(player)) {
            throw new RuntimeException("This player is already betting on this match!");
        }
        players.add(player);
    }
}
