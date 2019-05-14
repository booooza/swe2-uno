package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.state.State;

import java.io.Serializable;

public class Request implements Serializable {
    private Command command;
    private Object payload;

    public Request(Command command) {
        this.command = command;
    }

    public Request(Command command, Object payload) {
        this.command = command;
        this.payload = payload;
    }

    public Command getCommand() {
        return command;
    }

    public Object getPayload() {
        return payload;
    }

    public Object getCard () {
        if (!isBodyCard()) {
            throw new IllegalArgumentException();
        }
        return (CardInterface) payload;
    }

    public static enum Command {
        START,
        QUIT,
        PLAY,
        DRAW,
        GETSTATE;
    }

    private boolean isBodyCard() {
        return payload instanceof CardInterface;
    }
}
