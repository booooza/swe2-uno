package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("integration")
@DisplayName("Game Integration Tests")
public class GameTest {

    @Test
    @DisplayName("Register two players and initialize the game")
    void  testAddTwoPlayersAndInitialize() {
        // Given
        Game game = new Game();

        // When
        game.addPlayer("Marc");
        game.addPlayer("Luca");
        State state = game.initialize();

        // Then
        assertTrue(game.isRunning());
        assertEquals(2, state.getPlayers().size());
        assertNotNull(state.getTopDiscardPileCard());
        assertNotNull(state.getCurrentPlayer());
        assertEquals("Game initialized", state.getMessage());
        assertNull(state.getWinner());
        assertNotNull(state.getPlayerByName("Marc"));
    }

    @Test
    @DisplayName("testPlayCardLegal")
    void  testPlayCardLegal() {
        // Given
        Game game = new Game();

        // When
        game.addPlayer("Marc");
        game.addPlayer("Luca");
        State state = game.initialize();

        // Find out current player
        Optional<PlayerInterface> currentPlayer = state.getCurrentPlayer();
        CardInterface topDiscardPileCard = state.getTopDiscardPileCard();

        currentPlayer.ifPresent(c -> {

        });

        //

        // Then
    }

    @Test
    @DisplayName("testPlayCardIllegal")
    void  testPlayCardIllegal() {

    }

    @Test
    @DisplayName("testDrawCardLegal")
    void  testDrawCardLegal() {

    }

    @Test
    @DisplayName("testDrawCardIllegal")
    void  testDrawCardIllegal() {

    }
}
