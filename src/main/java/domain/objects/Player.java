package domain.objects;

import domain.constants.PlayerActionType;
import dto.Betting;
import domain.constants.BettingSide;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Player {
    private final UUID id;
    private int coins;
	private Casino casino;

    public Player(UUID id, Casino casino) {
        this.id = id;
        coins = 0;
		this.casino = casino;
    }

    public void deposit(int coinsToDeposit) {
        coins += coinsToDeposit;
    }

    public void withdraw(int coinsToWithdraw) {
        if (coins - coinsToWithdraw < 0) {
            casino.addIllegitimatePlayer(this);
        } else {
	        coins -= coinsToWithdraw;
        }
    }

    public void betOnMatch(int coinsToBet, Match match, BettingSide side) {
        try {
            Betting playerBetting = new Betting(this, match, coinsToBet, side);
            match.addBetting(playerBetting);
            withdraw(coinsToBet);
        } catch (Exception e) {
            casino.addIllegitimatePlayer(this);
        }
    }

	public void act(PlayerActionType type, int amount, Match match, BettingSide bettingSide) {
		switch (type) {
			case DEPOSIT -> deposit(amount);
			case WITHDRAW -> withdraw(amount);
			case BET -> betOnMatch(amount, match, bettingSide);
		}
	}
}
