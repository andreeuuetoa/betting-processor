import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BettingProcessor {
    private double aBetRate;
    private double bBetRate;
    private String winningSide;

    public void processBetting(Betting betting) {
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
