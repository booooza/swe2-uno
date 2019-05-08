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

    public State getState () {
        return (State) payload;
    }

    public void play () {
        if (isBodyCard()) {
            return;
        }
    }

    public Object start () {
        if (payload instanceof String) {
            return payload;
        }
        return "Error";
    }

    public boolean isBodyCard() {
        return payload instanceof CardInterface;
    }

    public static enum Command {
        START,
        QUIT,
        PLAY, 
        DRAW, 
        GETSTATE;
    }
}
