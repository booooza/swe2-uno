package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.business.card.UnoColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Player")
class PlayerTest {

	@Test
	@DisplayName("Test creation of player")
	void testCreationOfPlayer() {
		// Given
		PlayerInterface player1 = new Player("Marc");
		PlayerInterface player2 = new Player("Luca", 1L);

		// When

		// Then
		assertEquals(player1.getName(), "Marc");
		assertEquals(player2.getId(), 1L);
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
		player.addCard(new NumberCard(CardType.NUMBERCARD, UnoColor.RED, 1));

		// Then
		assertEquals(1, player.getHand().size());
	}

	@Test
	@DisplayName("Test set can draw flag")
	void testSetCanDrawFlag() {
		// Given
		PlayerInterface player = new Player("Marc");

		// When
		player.setCanDraw(true);

		// Then
		assertTrue(player.canDraw());
	}
}
