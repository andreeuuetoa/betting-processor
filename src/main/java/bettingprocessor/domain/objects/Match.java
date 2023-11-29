package bettingprocessor.domain.objects;

import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.returnmoneycalculator.ReturnMoneyCalculator;
import bettingprocessor.dto.Betting;
import bettingprocessor.dto.MatchData;
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

	public void updateGamesPlayedAndWonForPlayers() {
		for (Betting betting : bettings) {
			if (playerWhoMadeTheBettingIsLegitimate(betting)) {
				Player player = betting.getPlayer();
				player.increaseMatchesPlayedByOne();
				if (bettingIsAWinningOne(betting)) {
					player.increaseMatchesWonByOne();
				}
			}
		}
	}

	private boolean bettingIsAWinningOne(Betting betting) {
		return isBettingMadeOnWinningA(betting) || isBettingMadeOnWinningB(betting);
	}

	private boolean isBettingMadeOnWinningA(Betting betting) {
		return betting.getSide().equals(BettingSide.A) && getMatchData().getMatchOutcome().equals(MatchOutcome.A);
	}

	private boolean isBettingMadeOnWinningB(Betting betting) {
		return betting.getSide().equals(BettingSide.B) && getMatchData().getMatchOutcome().equals(MatchOutcome.B);
	}
}
