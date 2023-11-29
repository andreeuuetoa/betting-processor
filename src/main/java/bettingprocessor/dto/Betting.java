package bettingprocessor.dto;

import bettingprocessor.domain.objects.Match;
import bettingprocessor.domain.objects.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import bettingprocessor.domain.constants.BettingSide;

@Getter
@AllArgsConstructor
public class Betting {
    private Player player;
    private Match match;
    private int amount;
    private BettingSide side;
}
