package bettingprocessor;

import domain.Player;
import dto.Betting;
import dto.MatchData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import util.MatchOutcome;

@Getter
@AllArgsConstructor
public class BettingProcessor {
    private MatchData matchData;

    public void processBetting(Betting betting) {
        Player bettingPlayer = betting.getPlayer();
        if (betting.getSide().toString().equals(getMatchData().getMatchOutcome().toString())) {
            returnMoneyWithProfitToPlayer(betting, bettingPlayer);
        } else if (getMatchData().getMatchOutcome().equals(MatchOutcome.DRAW)) {
            returnBetMoneyToPlayer(bettingPlayer, betting.getAmount());
        }
    }

    private void returnMoneyWithProfitToPlayer(Betting betting, Player bettingPlayer) {
        if (getMatchData().getMatchOutcome().equals(MatchOutcome.A)) {
            bettingPlayer.deposit(getMoneyWithProfitOnASide(betting));
        } else if (getMatchData().getMatchOutcome().equals(MatchOutcome.B)) {
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
