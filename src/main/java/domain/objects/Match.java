package domain.objects;

import bettingprocessor.BettingProcessor;
import dto.Betting;
import dto.MatchData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Match {
    private MatchData matchData;
    private List<Betting> bettings;
    private Set<Player> illegalPlayers;

    public Match(MatchData matchData) {
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
        BettingProcessor bettingProcessorForMatch = new BettingProcessor(getMatchData());
        int casinoProfit = 0;

        for (Betting betting : bettings) {
            if (!illegalPlayers.contains(betting.getPlayer())) {
                int moneyGotBack = bettingProcessorForMatch.calculateMoneyGotBackFromBetting(betting);
                casinoProfit -= moneyGotBack - betting.getAmount();
            }
        }

        return casinoProfit;
    }

    public void payToPlayers() {
        BettingProcessor bettingProcessorForMatch = new BettingProcessor(getMatchData());

        for (Betting betting : bettings) {
            if (!illegalPlayers.contains(betting.getPlayer())) {
                int moneyGotBack = bettingProcessorForMatch.calculateMoneyGotBackFromBetting(betting);
                Player player = betting.getPlayer();
                player.deposit(moneyGotBack);
            }
        }
    }

    public void addIllegalPlayer(Player player) {
        illegalPlayers.add(player);
    }
}
