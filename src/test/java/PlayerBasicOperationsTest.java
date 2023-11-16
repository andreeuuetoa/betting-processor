import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerBasicOperationsTest {
    private Player player;
    private static MatchData sampleMatchData;

    @BeforeEach
    public void createPlayer() {
        player = new Player();
    }

    @BeforeAll
    public static void createSampleMatchData() {
        sampleMatchData = new MatchData(1.00, 0.75, "A");
    }

    @Test
    public void testPlayerInitialCoinbaseIsZero() {
        assertEquals(0, player.getCoins());
    }

    @Test
    public void testPlayerCanDeposit() {
        player.deposit(300);
        assertEquals(300, player.getCoins());
    }

    @Test
    public void testPlayerCannotWithdrawFromEmptyCoinbase() {
        assertThrows(RuntimeException.class, () -> player.withdraw(100));
    }

    @Test
    public void testPlayerCannotWithdrawFromInsufficientCoinbase() {
        player.deposit(30);
        assertThrows(RuntimeException.class, () -> player.withdraw(100));
    }

    @Test
    public void testPlayerCoinbaseDecreasesUponWithdrawal() {
        player.deposit(300);
        player.withdraw(100);
        assertEquals(200, player.getCoins());
    }

    @Test
    public void testPlayerBetsOnMatchAndCoinbaseDecreases() {
        player.deposit(100);
        Match match = new Match(sampleMatchData);
        player.betOnMatch(30, match, "A");
        assertEquals(70, player.getCoins());
    }

    @Test
    public void testPlayerCannotBetWithEmptyCoinbase() {
        Match match = new Match(sampleMatchData);
        assertThrows(RuntimeException.class, () -> player.betOnMatch(50, match, "A"));
    }

    @Test
    public void testPlayerCannotBetWithInsufficientCoinbase() {
        Match match = new Match(sampleMatchData);
        player.deposit(30);
        assertThrows(RuntimeException.class, () -> player.betOnMatch(50, match, "A"));
    }

    @Test
    public void testPlayerCanOnlyBetOnceOnAMatch() {
        Match match = new Match(sampleMatchData);
        player.deposit(300);
        player.betOnMatch(100, match, "A");
        assertThrows(RuntimeException.class, () -> player.betOnMatch(50, match, "A"));
    }
}
