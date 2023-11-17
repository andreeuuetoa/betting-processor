package domain;

import dto.Betting;
import util.BettingSide;

public class Player {
    private int coins;

    public Player() {
        coins = 0;
    }

    public int getCoins() {
        return coins;
    }

    public void deposit(int coinsToDeposit) {
        coins += coinsToDeposit;
    }

    public void withdraw(int coinsToWithdraw) {
        if (coins - coinsToWithdraw < 0) {
            throw new RuntimeException("domain.Player cannot withdraw more coins than he/she currently has.");
        }
        coins -= coinsToWithdraw;
    }

    public void betOnMatch(int coinsToBet, Match match, BettingSide side) {
        withdraw(coinsToBet);
        Betting playerBetting = new Betting(this, match, coinsToBet, side);
        match.addBetting(playerBetting);
    }
}