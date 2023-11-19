package util.objectgenerator;

import domain.objects.Casino;
import domain.objects.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerGeneratorTest {
    @Test
    public void testGeneratedPlayerHasId() {
        Player player = PlayerGenerator.generatePlayerWithRandomID(new Casino());
        assertNotNull(player.getId());
    }
}
