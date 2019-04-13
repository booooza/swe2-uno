package ch.swe2.uno.business.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Player")
public class PlayerTest {
    @Test
    @DisplayName("Creation of player")
    public void testCreationOfPlayer() {
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        PlayerInterface player = playerFactory.create("Player");
        assertEquals("Player", player.getName());
    }
}
