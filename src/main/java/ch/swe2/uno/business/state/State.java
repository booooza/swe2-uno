package ch.swe2.uno.business.state;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class State implements Serializable {
	private List<PlayerInterface> players;
	private CardInterface topDiscardPileCard;
	private String winner;
	private String message;

	public State() {
	}

	public State(List<PlayerInterface> players, String message) {
		this.players = players;
		this.message = message;
	}

	public synchronized Optional<PlayerInterface> getPlayerByName(String name) {
		return players.stream().filter(p -> name.equals(p.getName())).findFirst();
	}

	public synchronized Optional<PlayerInterface> getCurrentPlayer() {
		return players.stream().filter(PlayerInterface::isCurrentTurn).findFirst();
	}

	public synchronized void toggleCurrentTurn() {
		// TODO @Luca implement rotation logic
		players.forEach(p -> p.setCanDraw(true));
		players.forEach(PlayerInterface::toggleCurrentTurn);
	}

	public synchronized void addPlayer(String playerName) {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		this.players.add(new Player(playerName, (long) this.players.size()));
	}

	public synchronized Boolean containsPlayer(String playerName) {
		if (this.players != null) {
			return this.players.stream().anyMatch(p -> p.getName().equals(playerName));
		}
		return false;
	}

	public synchronized List<PlayerInterface> getPlayers() {
		return this.players;
	}

	public synchronized void setPlayers(List<PlayerInterface> players) {
		this.players = players;
	}

	public synchronized String getWinner() {
		return winner;
	}

	public synchronized void setWinner(String winner) {
		this.winner = winner;
	}

	public synchronized String getMessage() {
		return message;
	}

	public synchronized void setMessage(String message) {
		this.message = message;
	}

	public synchronized CardInterface getTopDiscardPileCard() {
		return topDiscardPileCard;
	}

	public synchronized void setTopDiscardPileCard(CardInterface topDiscardPileCard) {
		this.topDiscardPileCard = topDiscardPileCard;
	}
}
