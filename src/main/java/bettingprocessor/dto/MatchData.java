package bettingprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import bettingprocessor.domain.constants.MatchOutcome;

@Getter
@AllArgsConstructor
public class MatchData {
    private final double aBetRate;
    private final double bBetRate;
    private final MatchOutcome matchOutcome;
}
