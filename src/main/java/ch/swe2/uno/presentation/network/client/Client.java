package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	private ClientThread clientThread = null;
	public Client() {
		try {
			Socket socket = new Socket("localhost", 1234);
			clientThread = new ClientThread(socket);
		} catch (Exception ex) {
			logger.error(String.format("Error establishing connection to server. %s", ex.getMessage()));
		}
	}

	public State sendRequest(Request.Command command) {
		try {
			return clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER));
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		} finally {
			//socket.close();
		}
	}

	public State sendRequest(Request.Command command, String playerName) {
		try {
			return clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName));
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		} finally {
			//socket.close();
		}
	}

	public State sendRequest(Request.Command command, String playerName, CardInterface card, boolean uno) throws Exception {
		try {
			return clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName, card, uno));
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		} finally {
			// socket.close();
		}
	}
}
