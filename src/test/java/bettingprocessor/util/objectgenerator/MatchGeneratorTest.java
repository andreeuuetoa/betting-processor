package bettingprocessor.util.objectgenerator;

import bettingprocessor.domain.constants.MatchOutcome;
import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.dto.MatchData;
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
