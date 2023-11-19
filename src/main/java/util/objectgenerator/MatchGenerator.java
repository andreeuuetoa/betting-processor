package util.objectgenerator;

import domain.objects.Casino;
import domain.objects.Match;
import dto.MatchData;

import java.util.UUID;

public class MatchGenerator {
    public static Match generateMatchWithRandomID(MatchData matchData) {
        return new Match(UUID.randomUUID(), matchData, new Casino());
    }
}
