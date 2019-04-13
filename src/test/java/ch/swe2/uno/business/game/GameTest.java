package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerFactory;
import ch.swe2.uno.business.player.PlayerInterface;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Game")
public class GameTest {

    @BeforeAll
    public static void init(){
        Game game = Game.getInstance();
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        ArrayList<PlayerInterface> players = new ArrayList<>(2);
        players.add(playerFactory.create("Marc"));
        players.add(playerFactory.create("Luca"));
        game.initialize(players);
    }

    @Test
    @DisplayName("Test that player got cards")
    public void testPlayersHand() {
        Game game = Game.getInstance();
        assertTrue(game.getState().getPlayers().get(0).getHand().size() > 0);
        assertTrue(game.getState().getPlayers().get(1).getHand().size() > 0);
    }

    @Test
    @DisplayName("Test if top card is known")
    public void testTopCard() {
        Game game = Game.getInstance();
        assertTrue(game.getState().getTopCard() != null);
    }

    @Test
    @DisplayName("Test legal move")
    public void testLegalMove() {
        Game game = Game.getInstance();
        // Get player who's turn it is
        PlayerInterface currentPlayer = game.getCurrentPlayer();

        // Get playable card from players hand which matches topcard
        CardInterface playableCard = currentPlayer.getHand().stream().filter(c ->
                c.getColor() == game.getState().getTopCard().getColor() ||
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
}
