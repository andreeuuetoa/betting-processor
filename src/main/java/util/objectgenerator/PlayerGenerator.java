package util.objectgenerator;

import domain.objects.Player;

import java.util.UUID;

public class PlayerGenerator {
    public static Player generatePlayerWithRandomID() {
        return new Player(UUID.randomUUID());
    }
}
