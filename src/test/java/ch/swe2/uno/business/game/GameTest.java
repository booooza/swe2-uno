package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Game")
class GameTest {

    // Test data
    private static Game game = new Game();
    private PlayerInterface currentPlayer;

    @BeforeAll
    static void init(){
        List<PlayerInterface> players = new ArrayList<>(2);
        players.add(new Player("Marc"));
        players.add(new Player("Luca"));
        game.initialize(players);
    }

    @Test
    @DisplayName("Test that player got cards")
    void testPlayersHand() {
        assertTrue(game.getState().getPlayers().get(0).getHand().size() > 0);
        assertTrue(game.getState().getPlayers().get(1).getHand().size() > 0);
    }

    @Test
    @DisplayName("Test if top card is known")
    void testTopCard() {
        assertNotNull(game.getState().getTopCard());
    }

    @Test
    @DisplayName("Test legal move")
    void testLegalMove() {
        // Get player who's turn it is
        this.currentPlayer = game.getCurrentPlayer();

        // Get playable card from players hand which matches top card
        CardInterface playableCard = currentPlayer.getHand().stream().filter(c ->
                c.getColor().equals(game.getState().getTopCard().getColor()) ||
                        c.getNumber() == game.getState().getTopCard().getNumber())
                .findFirst()
                .orElse(null);

        if (playableCard != null) {
            game.playCard(currentPlayer, playableCard);
            assertEquals(game.getState().getTopCard(), playableCard);
        } else {
            game.drawCard(currentPlayer);
            assertNotEquals(game.getState().getTopCard(), playableCard);
        }
    }

    @Test
    @DisplayName("Test draw")
    void testDrawCard() {
        // Get player who's turn it is
        // this.currentPlayer = game.getCurrentPlayer();

        int handSize = 0;

        if (!currentPlayer.getHand().isEmpty()) {
            handSize = currentPlayer.getHand().size();
        }

        game.drawCard(currentPlayer);
        assertEquals(handSize + 1, currentPlayer.getHand().size());
    }
}
