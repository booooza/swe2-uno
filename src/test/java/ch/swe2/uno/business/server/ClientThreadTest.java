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

        // Then
        assertEquals(State.class, in.readObject().getClass());
    }


    @Test
    @DisplayName("Test client thread DRAW command")
    void testClientThreadDrawCommand() throws Exception {
        // Given
        Socket socket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // When
        out.writeObject(new Request(Request.Command.DRAW, "Marc"));
        out.writeObject(new Request(Request.Command.QUIT));

        // Then
        assertEquals(State.class, in.readObject().getClass());
    }

    @Test
    @DisplayName("Test client thread GETSTATE command")
    void testClientThreadGetStateCommand() throws Exception {
        // Given
        Socket socket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // When

        out.writeObject(new Request(Request.Command.GETSTATE));
        State state = ((State) (in.readObject()));

        // Then
        assertEquals("Game initialized", state.getMessage());
    }
}
