import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Match {
    private final double aBetRate;
    private final double bBetRate;
    private final char winningSide;
}
