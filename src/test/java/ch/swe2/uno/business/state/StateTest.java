package ch.swe2.uno.business.state;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State Test")
class StateTest {

	@Test
	@DisplayName("setPlayers")
	void setGetPlayers() {
		// Given
		State state = new State();
		List<PlayerInterface> players = new ArrayList<>();
		PlayerInterface player1 = new Player("Marc");
		PlayerInterface player2 = new Player("Luca");
		players.add(player1);
		players.add(player2);

		// When
		state.setPlayers(players);

		// Then
		assertEquals(players, state.getPlayers());
		assertEquals(Optional.of(player1), state.getPlayerByName("Marc"));
	}

	@Test
	@DisplayName("toggleCurrentTurn")
	void toggleCurrentTurn() {
		// Given
		List<PlayerInterface> players = new ArrayList<>();
		PlayerInterface player1 = new Player("Marc");
		PlayerInterface player2 = new Player("Luca");
		player1.setCurrentTurn(true);
		players.add(player1);
		players.add(player2);

		// When
		State state = new State(players, "Initialized");
		state.toggleCurrentTurn();

		// Then
		assertEquals(Optional.of(player2), state.getCurrentPlayer());
		assertFalse(player1.isCurrentTurn());
		assertTrue(player2.isCurrentTurn());
	}

	@Test
	@DisplayName("setWinner")
	void setGetWinner() {
		// Given
		State state = new State();

		// When
		state.setWinner("Marc");

		// Then
		assertEquals("Marc", state.getWinner());
	}

	@Test
	@DisplayName("setMessage")
	void setGetMessage() {
		// Given
		State state = new State();

		// When
		state.setMessage("Message");

		// Then
		assertEquals("Message", state.getMessage());
	}

	@Test
	@DisplayName("setTopDiscardPileCard")
	void setGetTopDiscardPileCard() {
		// Given
		State state = new State();
		CardInterface card = new NumberCard(CardType.NUMBERCARD, UnoColor.RED, 0);

		// When
		state.setTopDiscardPileCard(card);

		// Then
		assertEquals(card, state.getTopDiscardPileCard());
	}
}