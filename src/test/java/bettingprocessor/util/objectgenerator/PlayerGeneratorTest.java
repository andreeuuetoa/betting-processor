package bettingprocessor.util.objectgenerator;

import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerGeneratorTest {
    @Test
    public void testGeneratedPlayerHasId() {
        Player player = PlayerGenerator.generatePlayerWithRandomID(new Casino());
        assertNotNull(player.getId());
    }
}
