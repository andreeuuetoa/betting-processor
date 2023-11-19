package util.objectgenerator;

import domain.objects.Casino;
import domain.objects.Player;

import java.util.UUID;

public class PlayerGenerator {
    public static Player generatePlayerWithRandomID(Casino casino) {
        return new Player(UUID.randomUUID(), casino);
    }
}
