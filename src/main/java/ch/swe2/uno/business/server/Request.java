package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.state.State;

import java.io.Serializable;

public class Request implements Serializable {
    private Command command;
    private Direction direction;
    private String playerName;
    private CardInterface card;
    private boolean uno;

    public Request(Command command, Direction direction) {
        this.command = command;
        this.direction = direction;
    }

    public Request(Command command, Direction direction, String playerName) {
        this.command = command;
        this.direction = direction;
        this.playerName = playerName;
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

    public Command getCommand() {
        return command;
    }

    public Direction getDirection() { return direction; }

    public String getPlayerName() {
        return playerName;
    }

    boolean getUno() {
        return uno;
    }

    public CardInterface getCard () {
        return card;
    }


    public enum Direction{
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
