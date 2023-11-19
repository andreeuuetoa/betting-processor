package util.objectgenerator;

import domain.constants.MatchOutcome;
import domain.objects.Casino;
import domain.objects.Match;
import dto.MatchData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MatchGeneratorTest {
    @Test
    public void testGeneratedPlayerHasId() {
        MatchData matchData = new MatchData(1.00, 0.75, MatchOutcome.A);
        Match match = MatchGenerator.generateMatchWithRandomID(matchData, new Casino());
        assertNotNull(match.getId());
    }
}
