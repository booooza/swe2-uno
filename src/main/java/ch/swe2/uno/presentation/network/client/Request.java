package ch.swe2.uno.presentation.network.client;

public class Request {
    private Command command;

    public Request(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static enum Command {
        QUIT,
        GETSTATE;
    }
}
