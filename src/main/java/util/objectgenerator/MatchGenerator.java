package util.objectgenerator;

import domain.objects.Casino;
import domain.objects.Match;
import dto.MatchData;

import java.util.UUID;

public class MatchGenerator {
    public static Match generateMatchWithRandomID(MatchData matchData, Casino casino) {
        return new Match(UUID.randomUUID(), matchData, casino);
    }
}
