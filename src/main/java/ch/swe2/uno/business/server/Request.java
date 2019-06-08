package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.state.State;

import java.io.Serializable;

public class Request implements Serializable {
    private Command command;
    private String playerName;
    private CardInterface card;
    private boolean uno;
    private UnoColor chosenColor;

    public Request(Command command) {
        this.command = command;
    }

    public Request(Command command, String playerName) {
        this.command = command;
        this.playerName = playerName;
    }

    public Request(Command command, String playerName, CardInterface card) {
        this.command = command;
        this.playerName = playerName;
        this.card = card;
    }

    public Request(Command command, String playerName, CardInterface card, boolean uno) {
        this.command = command;
        this.playerName = playerName;
        this.card = card;
        this.uno = uno;
    }

    public Request(Command command, String playerName, CardInterface card, boolean uno, UnoColor chosenColor) {
        this.command = command;
        this.playerName = playerName;
        this.card = card;
        this.uno = uno;
        this.chosenColor = chosenColor;
    }

    Command getCommand() {
        return command;
    }

    String getPlayerName() {
        return playerName;
    }

    boolean getUno() {
        return uno;
    }

    public CardInterface getCard () {
        return card;
    }

    UnoColor getChosenColor() {return chosenColor; };

    public enum Command {
        JOIN,
        START,
        RESTART,
        QUIT,
        PLAY,
        CHECK,
        DRAW,
        GETSTATE;
	}

}
