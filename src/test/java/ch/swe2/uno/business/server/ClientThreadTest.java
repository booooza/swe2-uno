package ch.swe2.uno.business.server;

import ch.swe2.uno.business.state.State;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
@DisplayName("Client Thread Integration Tests (server needs to run)")
class ClientThreadTest {

    @Test
    @DisplayName("Test client thread START command")
    void testClientThreadStartCommand() throws Exception {

        // Given
        Socket socket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // When
        out.writeObject(new Request(Request.Command.START, "Marc"));
        out.writeObject(new Request(Request.Command.QUIT));

        // Then
        assertEquals("Hello Marc", in.readObject());
    }

    @Test
    @DisplayName("Test client thread PLAY command")
    void testClientThreadPlayCommand() throws Exception {
        // Given
        Socket socket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // When
        out.writeObject(new Request(Request.Command.START, "Marc"));
        out.writeObject(new Request(Request.Command.START, "Luca"));
        out.writeObject(new Request(Request.Command.GETSTATE));
        State stateBeforeCardIsPlayer = (State) in.readObject();

        // out.writeObject(new Request(Request.Command.QUIT));

        // Then

    }

    @Test
    @DisplayName("Test client thread DRAW command")
    void testClientThreadDrawCommand() throws Exception {
        // Given

        // When

        // Then

    }

    @Test
    @DisplayName("Test client thread GETSTATE command")
    void testClientThreadGetStateCommand() throws Exception {
        // Given

        // When

        // Then
    }
}
