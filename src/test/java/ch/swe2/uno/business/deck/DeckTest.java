package ch.swe2.uno.business.deck;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Deck")
public class DeckTest {

	@Test
	@DisplayName("Number of cards in deck")
	void testCreateDeck() {
		// Given
		Deck deck = new Deck();

		// When
		deck.create();

		// Then
		assertEquals(79, deck.getDrawPileSize());
	}

	@Test
	@DisplayName("Test distribution of card to players")
	void testDistributeCardsToPlayer() {
		// Given
		Deck deck = new Deck();
		List<PlayerInterface> players = new ArrayList<>(2);
		// TODO: somehow mock the players to isolate from SUT
		players.add(new Player("Marc"));
		players.add(new Player("Luca"));

		// When
		deck.create();
		deck.distribute(players);

		// Then
		assertEquals(65, deck.getDrawPileSize());
	}

	@Test
	@DisplayName("Test drawing a single card from the draw pile")
	void testDrawSingleCard() {
		// Given
		Deck deck = new Deck();

		// When
		deck.create();
		CardInterface drawnCard = deck.drawCard();

		// Then
		assertEquals(78, deck.getDrawPileSize());
	}

	@Test
	@DisplayName("Test drawing all card from the draw pile")
	void testDrawAllCards() {
		// Given
		Deck deck = new Deck();

		// When
		deck.create();

		for (int i = 0; i < 80; i++) {
			deck.addCardToDiscardPile(deck.drawCard());
		}

		assertEquals(78, deck.getDrawPileSize());
		assertEquals(1, deck.getDiscardPileSize());
	}
}
