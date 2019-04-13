package ch.swe2.uno.business.server;

import ch.swe2.uno.business.state.State;

import java.io.Serializable;

public class Request implements Serializable {
    private Command command;
    private Object payload;

    public Request(Command command, Object payload) {
        this.command = command;
        this.payload = payload;
    }

    public Command getCommand() {
        return command;
    }

    public State getState () {
        return (State) payload;
    }

    public static enum Command {
        QUIT,
        GETSTATE;
    }
}
