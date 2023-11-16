import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Betting {
    private Player player;
    private Match match;
    private int amount;
}
