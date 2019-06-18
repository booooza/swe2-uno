package ch.swe2.uno.business.server;

import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.network.client.Client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Client Thread Integration Tests")
public class ClientHandlerThreadTest {
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private static Thread serverThread = null;

	@BeforeAll
	static void initServerSocket() {
		String[] args = new String[] {};
		serverThread = new Thread(() -> {
			try {
				Server.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(serverThread);
	}

	@AfterAll
	static void endServerSocket() throws Exception {
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 1234);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(new Request(Request.Command.QUIT, Request.Direction.CLIENT_TO_SERVER, "Marc"));
			assertEquals(false, Client.hostAvailabilityCheck());
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		shutdownAndAwaitTermination(threadPool);
	}

	static void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			pool.shutdownNow(); // Cancel currently executing tasks
		} catch (Exception ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	@Test
	@Order(1)
	@DisplayName("Test client thread JOIN command")
	void testClientJoin() throws Exception {
		// Given
		Socket socket = new Socket("127.0.0.1", 1234);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		// When
		out.writeObject(new Request(Request.Command.JOIN, Request.Direction.CLIENT_TO_SERVER, "Marc"));

		// Then
		State state = ((State) (in.readObject()));
		assertEquals(State.class, state.getClass());

		socket.close();
	}

	@Test
	@Order(2)
	@DisplayName("Test client thread START command")
	void testClientThreadStartCommand() throws Exception {
		// Given
		Socket socket = new Socket("127.0.0.1", 1234);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		// When
		out.writeObject(new Request(Request.Command.START, Request.Direction.CLIENT_TO_SERVER, "Marc"));

		// Then
		State state = ((State) (in.readObject()));
		assertEquals(State.class, state.getClass());

		socket.close();
	}

	@Test
	@Order(3)
	@DisplayName("Test client thread DRAW command")
	void testClientThreadDrawCommand() throws Exception {
		// Given
		Socket socket = new Socket("127.0.0.1", 1234);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		// When
		out.writeObject(new Request(Request.Command.DRAW, Request.Direction.CLIENT_TO_SERVER, "Marc"));

		// Then
		State state = ((State) (in.readObject()));
		assertEquals(State.class, state.getClass());

		socket.close();
	}

	@Test
	@Order(4)
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

		socket.close();
	}
}
