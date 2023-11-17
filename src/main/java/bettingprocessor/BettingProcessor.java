package bettingprocessor;

import dto.Betting;
import dto.MatchData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import util.BettingSide;
import util.MatchOutcome;

@Getter
@AllArgsConstructor
public class BettingProcessor {
    private MatchData matchData;

    public int calculateMoneyGotBackFromBetting(Betting betting) {
        if (isBettingMadeOnWinningA(betting)) {
            return getMoneyWithProfitOnASide(betting);
        } else if (isBettingMadeOnWinningB(betting)) {
            return getMoneyWithProfitOnBSide(betting);
        } else if (isMatchADraw()) {
            return betting.getAmount();
        }
        return 0;
    }

    private boolean isBettingMadeOnWinningA(Betting betting) {
        return betting.getSide().equals(BettingSide.A) && getMatchData().getMatchOutcome().equals(MatchOutcome.A);
    }

    private boolean isBettingMadeOnWinningB(Betting betting) {
        return betting.getSide().equals(BettingSide.B) && getMatchData().getMatchOutcome().equals(MatchOutcome.B);
    }

    private boolean isMatchADraw() {
        return getMatchData().getMatchOutcome().equals(MatchOutcome.DRAW);
    }

    private int getMoneyWithProfitOnASide(Betting betting) {
        return (int) (betting.getAmount() * getMatchData().getABetRate());
    }

    private int getMoneyWithProfitOnBSide(Betting betting) {
        return (int) (betting.getAmount() * getMatchData().getBBetRate());
    }
}
