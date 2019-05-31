package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.state.State;

import java.io.Serializable;

public class Request implements Serializable {
    private Command command;
    private String playerName;
    private CardInterface card;

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

    public Command getCommand() {
        return command;
    }

    public Object getPayload() {
        return card;
    }

    public String getPlayerName() {
        return playerName;
    }

    public CardInterface getCard () {
        return card;
    }

    public static enum Command {
        START,
        QUIT,
        PLAY,
        DRAW,
        GETSTATE;
    }

}
