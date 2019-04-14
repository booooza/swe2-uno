package ch.swe2.uno.presentation.network.client;

public class Request {
    private Command command;

    public Request(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public enum Command {
        QUIT,
        GETSTATE;
    }
}
