package bettingprocessor.util.objectgenerator;

import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.dto.MatchData;

import java.util.UUID;

public class MatchGenerator {
    public static Match generateMatchWithRandomID(MatchData matchData, Casino casino) {
        return new Match(UUID.randomUUID(), matchData, casino);
    }
}
