package ch.swe2.uno.business.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Player")
public class PlayerTest {
    @Test
    @DisplayName("Name")
    public void testIfPlayerGetsCorrectName() {
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        IPlayer player = playerFactory.createPlayer("Player");
        String expected = "Player";
        String actual = player.getName();
        assertEquals(expected, actual);
    }
}
