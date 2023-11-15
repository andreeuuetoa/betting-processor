import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasinoTest {
    @Test
    public void testCasinoInitialBalanceIsZero() {
        Casino casino = new Casino();
        assertEquals(0, casino.getBalance());
    }
}
