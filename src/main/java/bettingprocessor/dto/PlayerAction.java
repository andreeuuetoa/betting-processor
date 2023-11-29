package bettingprocessor.dto;

import bettingprocessor.domain.constants.BettingSide;
import bettingprocessor.domain.constants.PlayerActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PlayerAction {
    private UUID playerId;
    private PlayerActionType type;
    private UUID matchId;
    private int amount;
    private BettingSide bettingSide;
}
