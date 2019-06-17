package ch.swe2.uno.business.server;

import ch.swe2.uno.business.state.State;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@DisplayName("Client Thread Integration Tests (server needs to run)")
class ClientHandlerThreadTest {

	private Thread serverThread = null;

	@BeforeAll
	void initServerSocket() {
		String[] args = new String[] {};
		this.serverThread = new Thread(() -> {
			try {
				Server.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@AfterAll
	void endServerSocket() {
		serverThread.stop();
	}

	@Test
	@DisplayName("Test client thread START command")
	void testClientThreadStartCommand() throws Exception {
		// Given
		Socket socket = new Socket("127.0.0.1", 1234);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		// When
		out.writeObject(new Request(Request.Command.START, Request.Direction.CLIENT_TO_SERVER, "Marc"));

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
		out.writeObject(new Request(Request.Command.DRAW, Request.Direction.CLIENT_TO_SERVER, "Marc"));
		out.writeObject(new Request(Request.Command.QUIT, Request.Direction.CLIENT_TO_SERVER));

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

		out.writeObject(new Request(Request.Command.GETSTATE, Request.Direction.CLIENT_TO_SERVER));
		State state = ((State) (in.readObject()));

		// Then
		assertEquals("Game initialized", state.getMessage());
	}
}
