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
            returnMoneyWithProfitToPlayer(betting, bettingPlayer);
        } else if (getWinningSide().equals("DRAW")) {
            returnBetMoneyToPlayer(bettingPlayer, betting.getAmount());
        }
    }

    private void returnMoneyWithProfitToPlayer(Betting betting, Player bettingPlayer) {
        if (getWinningSide().equals("A")) {
            bettingPlayer.deposit(getMoneyWithProfitOnASide(betting));
        } else if (getWinningSide().equals("B")) {
            bettingPlayer.deposit(getMoneyWithProfitOnBSide(betting));
        }
    }

    private int getMoneyWithProfitOnASide(Betting betting) {
        return (int) (betting.getAmount() * aBetRate);
    }

    private int getMoneyWithProfitOnBSide(Betting betting) {
        return (int) (betting.getAmount() * bBetRate);
    }

    private void returnBetMoneyToPlayer(Player bettingPlayer, int betting) {
        bettingPlayer.deposit(betting);
    }
}
