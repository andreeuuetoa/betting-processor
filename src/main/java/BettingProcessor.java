import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BettingProcessor {
    private MatchData matchData;

    public void processBetting(Betting betting) {
        Player bettingPlayer = betting.getPlayer();
        if (betting.getSide().equals(getMatchData().getWinningSide())) {
            returnMoneyWithProfitToPlayer(betting, bettingPlayer);
        } else if (getMatchData().getWinningSide().equals("DRAW")) {
            returnBetMoneyToPlayer(bettingPlayer, betting.getAmount());
        }
    }

    private void returnMoneyWithProfitToPlayer(Betting betting, Player bettingPlayer) {
        if (getMatchData().getWinningSide().equals("A")) {
            bettingPlayer.deposit(getMoneyWithProfitOnASide(betting));
        } else if (getMatchData().getWinningSide().equals("B")) {
            bettingPlayer.deposit(getMoneyWithProfitOnBSide(betting));
        }
    }

    private int getMoneyWithProfitOnASide(Betting betting) {
        return (int) (betting.getAmount() * getMatchData().getABetRate());
    }

    private int getMoneyWithProfitOnBSide(Betting betting) {
        return (int) (betting.getAmount() * getMatchData().getBBetRate());
    }

    private void returnBetMoneyToPlayer(Player bettingPlayer, int betting) {
        bettingPlayer.deposit(betting);
    }
}
