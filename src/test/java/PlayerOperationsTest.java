import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerOperationsTest {
    @Test
    public void testPlayerInitialCoinbaseIsZero() {
        Player player = new Player();
        assertEquals(0, player.getCoins());
    }

    @Test
    public void testPlayerCanDeposit() {
        Player player = new Player();
        player.deposit(300);
        assertEquals(300, player.getCoins());
    }

    @Test
    public void testPlayerCannotWithdrawFromEmptyCoinbase() {
        Player player = new Player();
        assertThrows(RuntimeException.class, () -> player.withdraw(100));
    }

    @Test
    public void testPlayerCannotWithdrawFromInsufficientCoinbase() {
        Player player = new Player();
        player.deposit(30);
        assertThrows(RuntimeException.class, () -> player.withdraw(100));
    }

    @Test
    public void testPlayerCoinbaseDecreasesUponWithdrawal() {
        Player player = new Player();
        player.deposit(300);
        player.withdraw(100);
        assertEquals(200, player.getCoins());
    }
}
