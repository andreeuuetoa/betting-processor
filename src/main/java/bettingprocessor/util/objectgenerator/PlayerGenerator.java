package bettingprocessor.util.objectgenerator;

import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Player;

import java.util.UUID;

public class PlayerGenerator {
    public static Player generatePlayerWithRandomID(Casino casino) {
        return new Player(UUID.randomUUID(), casino);
    }
}
