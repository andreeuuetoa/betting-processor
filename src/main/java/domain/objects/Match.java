package domain.objects;

import returnmoneycalculator.ReturnMoneyCalculator;
import dto.Betting;
import dto.MatchData;
import lombok.Getter;

import java.util.*;

@Getter
public class Match {
    private final UUID id;
    private MatchData matchData;
    private List<Betting> bettings;
    private Casino casino;

    public Match(UUID id, MatchData matchData, Casino casino) {
        this.id = id;
        this.matchData = matchData;
        bettings = new ArrayList<>();
        this.casino = casino;
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

    public int calculateCasinoProfit() {
        ReturnMoneyCalculator returnMoneyCalculatorForMatch = new ReturnMoneyCalculator(getMatchData());
        int casinoProfit = 0;

        for (Betting betting : bettings) {
            if (playerWhoMadeTheBettingIsLegitimate(betting)) {
                int moneyGotBack = returnMoneyCalculatorForMatch.calculateMoneyGotBackFromBetting(betting);
                casinoProfit -= moneyGotBack - betting.getAmount();
            }
        }

        return casinoProfit;
    }

	public void payToPlayers() {
        ReturnMoneyCalculator returnMoneyCalculatorForMatch = new ReturnMoneyCalculator(getMatchData());

        for (Betting betting : bettings) {
            if (playerWhoMadeTheBettingIsLegitimate(betting)) {
                int moneyGotBack = returnMoneyCalculatorForMatch.calculateMoneyGotBackFromBetting(betting);
                Player player = betting.getPlayer();
                player.deposit(moneyGotBack);
            }
        }
    }

	private boolean playerWhoMadeTheBettingIsLegitimate(Betting betting) {
		return casino.getLegitimatePlayers().contains(betting.getPlayer());
	}
}
