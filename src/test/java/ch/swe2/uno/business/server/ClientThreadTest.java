package ch.swe2.uno.business.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Client Thread")
class ClientThreadTest {

    @Test
    @DisplayName("Test client thread START command")
    void testClientThreadStartCommand() throws Exception {
        // Given
        Socket socket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        out.writeObject(new Request(Request.Command.START, "Marc"));
        out.writeObject(new Request(Request.Command.QUIT));

        assertEquals("Hello Marc", in.readObject());
    }

    @Test
    @DisplayName("Test client thread PLAY command")
    void testClientThreadPlayCommand() throws Exception {

    }

    @Test
    @DisplayName("Test client thread DRAW command")
    void testClientThreadDrawCommand() throws Exception {

    }

    @Test
    @DisplayName("Test client thread GETSTATE command")
    void testClientThreadGetStateCommand() throws Exception {

    }
}
