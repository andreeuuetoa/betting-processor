import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Betting {
    private Player player;
    private Match match;
    private int amount;
    private BettingSide side;
}
