package domain.objects;

import dto.Betting;
import domain.constants.BettingSide;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Player {
    private final UUID id;
    private int coins;

    public Player(UUID id) {
        this.id = id;
        coins = 0;
    }

    public void deposit(int coinsToDeposit) {
        coins += coinsToDeposit;
    }

    public void withdraw(int coinsToWithdraw) {
        if (coins - coinsToWithdraw < 0) {
            throw new RuntimeException("Player cannot withdraw more coins than he/she currently has.");
        }
        coins -= coinsToWithdraw;
    }

    public void betOnMatch(int coinsToBet, Match match, BettingSide side) {
        try {
            Betting playerBetting = new Betting(this, match, coinsToBet, side);
            match.addBetting(playerBetting);
            withdraw(coinsToBet);
        } catch (Exception e) {
            match.addIllegitimatePlayer(this);
        }
    }
}
