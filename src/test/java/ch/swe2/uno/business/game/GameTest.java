package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.ActionCard;
import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.state.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("network")
@DisplayName("Game Integration Tests")
public class GameTest {

	@Test
	@DisplayName("Register two players and initialize the game")
	void testAddTwoPlayersAndInitialize() {
		// Given
		Game game = new Game(new Deck());

		// When
		game.initialize();
		game.addPlayer("Marc");
		game.addPlayer("Luca");
		State state = game.start();

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
	@DisplayName("Wild card is the top card on the discard pile")
	void testTopDiscardPileCardIsWildCard() {
		// Given
		Deck deck = new Deck();
		CardInterface anyCard;
		CardInterface wildcard = new ActionCard(CardType.WILD, UnoColor.BLACK, 88);
		deck.addCardToDiscardPile(wildcard);
		Game game = new Game(deck);

		// When
		game.initialize();
		game.addPlayer("Marc");
		game.addPlayer("Luca");
		game.start();

		anyCard = game.getState().getCurrentPlayer().get().getHand().get(0);
		game.playCard(game.getState().getCurrentPlayer().get().getName(), anyCard, false, UnoColor.RED);

		// Then
		assertEquals(game.getState().getTopDiscardPileCard().getNumber(), anyCard.getNumber());
	}
}
