package dto;

import domain.Match;
import domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import util.BettingSide;

@Getter
@AllArgsConstructor
public class Betting {
    private Player player;
    private Match match;
    private int amount;
    private BettingSide side;
}
