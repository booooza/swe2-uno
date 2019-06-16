package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.state.State;

import java.io.Serializable;

public class Request implements Serializable {
	private Command command;
	private Direction direction;
	private String playerName;
	private CardInterface card;
	private boolean uno;
	private UnoColor chosenColor;
	private State state;

	public Request(Command command, Direction direction) {
		this.command = command;
		this.direction = direction;
	}

	public Request(Command command, Direction direction, String playerName) {
		this.command = command;
		this.direction = direction;
		this.playerName = playerName;
	}

	public Request(Command command, Direction direction, State state) {
		this.command = command;
		this.direction = direction;
		this.state = state;
	}

	public Request(Command command, Direction direction, String playerName, CardInterface card) {
		this.command = command;
		this.direction = direction;
		this.playerName = playerName;
		this.card = card;
	}

	public Request(Command command, Direction direction, String playerName, CardInterface card, boolean uno) {
		this.command = command;
		this.direction = direction;
		this.playerName = playerName;
		this.card = card;
		this.uno = uno;
	}

	public Request(Command command, Direction direction, String playerName, CardInterface card, boolean uno, UnoColor chosenColor) {
		this.command = command;
		this.direction = direction;
		this.playerName = playerName;
		this.card = card;
		this.uno = uno;
		this.chosenColor = chosenColor;
	}

	public Command getCommand() {
		return command;
	}

	Direction getDirection() {
		return direction;
	}

	String getPlayerName() {
		return playerName;
	}

	public State getState() {
		return state;
	}

	boolean getUno() {
		return uno;
	}

	public CardInterface getCard() {
		return card;
	}

	UnoColor getChosenColor() {
		return chosenColor;
	}

	public enum Direction {
		SERVER_TO_CLIENT,
		CLIENT_TO_SERVER
	}

	public enum Command {
		SUBSCRIBE,
		JOIN,
		JOINED,
		START,
		STARTED,
		RESTART,
		QUIT,
		PLAY,
		PLAYED,
		CHECK,
		DRAW,
		GETSTATE;
	}

}
