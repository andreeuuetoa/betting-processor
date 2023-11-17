package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import util.MatchOutcome;

@Getter
@AllArgsConstructor
public class MatchData {
    private final double aBetRate;
    private final double bBetRate;
    private final MatchOutcome matchOutcome;
}
