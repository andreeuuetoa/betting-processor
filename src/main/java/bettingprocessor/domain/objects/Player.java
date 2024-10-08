package bettingprocessor.domain.objects;

import bettingprocessor.domain.constants.PlayerActionType;
import bettingprocessor.dto.Betting;
import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.dto.PlayerAction;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Player {
    private final UUID id;
    private int coins;
	private final Casino casino;
	private int matchesPlayed;
	private int matchesWon;

    public Player(UUID id, Casino casino) {
        this.id = id;
        coins = 0;
		this.casino = casino;
		matchesPlayed = 0;
		matchesWon = 0;
    }

    public void deposit(int coinsToDeposit) {
        coins += coinsToDeposit;
    }

    public void withdraw(int coinsToWithdraw) {
        if (coins - coinsToWithdraw < 0) {
            casino.addIllegitimatePlayer(this);
			casino.addIllegitimatePlayerAction(new PlayerAction(getId(), PlayerActionType.WITHDRAW, null, coinsToWithdraw, null));
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
			casino.addIllegitimatePlayerAction(new PlayerAction(getId(), PlayerActionType.BET, match.getId(), coinsToBet, side));
        }
    }

	public double getWinRate() {
		if (getMatchesPlayed() == 0) {
			return 0;
		}
		return (double) getMatchesWon() / getMatchesPlayed();
	}

	public void increaseMatchesPlayedByOne() {
		matchesPlayed++;
	}

	public void increaseMatchesWonByOne() {
		matchesWon++;
	}
}
