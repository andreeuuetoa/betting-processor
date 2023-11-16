import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTest {
    @Test
    public void testCreateSampleMatch() {
        Match sampleMatch = new Match(1.00, 0.75, 'A');

        assertEquals(1, sampleMatch.getABetRate());
        assertEquals(0.75, sampleMatch.getBBetRate());
        assertEquals('A', sampleMatch.getWinningSide());
    }
}
