package dto;

import domain.objects.Match;
import domain.objects.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import domain.constants.BettingSide;

@Getter
@AllArgsConstructor
public class Betting {
    private Player player;
    private Match match;
    private int amount;
    private BettingSide side;
}
