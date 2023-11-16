import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Match {
    private final double aBetRate;
    private final double bBetRate;
    private final String winningSide;
    private List<Betting> bettings;

    public Match(double aBetRate, double bBetRate, String winningSide) {
        this.aBetRate = aBetRate;
        this.bBetRate = bBetRate;
        this.winningSide = winningSide;
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
            processBetting(betting);
        }
    }

    private void processBetting(Betting betting) {
        Player bettingPlayer = betting.getPlayer();
        if (betting.getSide().equals(getWinningSide())) {
            if (getWinningSide().equals("A")) {
                bettingPlayer.deposit((int) (betting.getAmount() * aBetRate));
            } else if (getWinningSide().equals("B")) {
                bettingPlayer.deposit((int) (betting.getAmount() * bBetRate));
            }
        }
    }
}
