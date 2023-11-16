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

    @Test
    public void testCheckBettingDoneOnAMatch() {
        Match sampleMatch = new Match(1.00, 0.75, 'A');
        Player player = new Player();

        player.deposit(100);
        player.betOnMatch(30, sampleMatch);

        assertEquals(1, sampleMatch.getBettings().size());

        Betting singleBettingOnMatch = sampleMatch.getBettings().get(0);
        assertEquals(player, singleBettingOnMatch.getPlayer());
        assertEquals(sampleMatch, singleBettingOnMatch.getMatch());
        assertEquals(30, singleBettingOnMatch.getAmount());
    }
}
