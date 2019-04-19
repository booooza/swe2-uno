package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Player")
class PlayerTest {
    @Mock
    List<CardInterface> hand;

    @Mock
    CardInterface card;

    @Test
    @DisplayName("Test creation of player")
    void testCreationOfPlayer() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When

        // Then
        assertEquals(player.getName(), "Marc");
    }

    @Test
    @DisplayName("Test setting the hand of the player")
    void testSettingPlayerHand() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When
        player.setHand(hand);

        // Then
        assertEquals(player.getHand(), hand);
    }

    @Test
    @DisplayName("Test setting current turn of the player")
    void testSettingPlayerCurrentTurn() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When
        player.setCurrentTurn(true);

        // Then
        assertTrue(player.isCurrentTurn());
    }

    @Test
    @DisplayName("Test toggle current turn of the player")
    void testTogglePlayerCurrentTurn() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When
        player.setCurrentTurn(false);
        player.toggleCurrentTurn();

        // Then
        assertTrue(player.isCurrentTurn());
    }

    @Test
    @DisplayName("Test can end turn flag of player")
    void testCanEndTurnFlagOfPlayer() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When
        player.setCanEndTurn(true);

        // Then
        assertTrue(player.canEndTurn());
    }

    @Test
    @DisplayName("Test uno of player")
    void testSettingPlayerUno() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When
        player.setUno(true);

        // Then
        assertTrue(player.isUno());
    }

    @Test
    @DisplayName("Test add card to player")
    void testAddCardToPlayer() {
        // Given
        PlayerInterface player = new Player("Marc");

        // When
        player.addCard(card);

        // Then
        assertEquals(1, player.getHand().size());
    }
}
