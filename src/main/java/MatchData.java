import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchData {
    private final double aBetRate;
    private final double bBetRate;
    private final String winningSide;
}
