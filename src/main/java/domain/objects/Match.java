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
    private Set<Player> illegalPlayers;

    public Match(UUID id, MatchData matchData) {
        this.id = id;
        this.matchData = matchData;
        bettings = new ArrayList<>();
        illegalPlayers = new HashSet<>();
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
            if (!illegalPlayers.contains(betting.getPlayer())) {
                int moneyGotBack = returnMoneyCalculatorForMatch.calculateMoneyGotBackFromBetting(betting);
                casinoProfit -= moneyGotBack - betting.getAmount();
            }
        }

        return casinoProfit;
    }

    public void payToPlayers() {
        ReturnMoneyCalculator returnMoneyCalculatorForMatch = new ReturnMoneyCalculator(getMatchData());

        for (Betting betting : bettings) {
            if (!illegalPlayers.contains(betting.getPlayer())) {
                int moneyGotBack = returnMoneyCalculatorForMatch.calculateMoneyGotBackFromBetting(betting);
                Player player = betting.getPlayer();
                player.deposit(moneyGotBack);
            }
        }
    }

    public void addIllegalPlayer(Player player) {
        illegalPlayers.add(player);
    }
}
