package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	protected ExecutorService threadPool =
			Executors.newFixedThreadPool(2);
	private ClientThread clientThread = null;
	private ClientRequestListener clientRequestListener = null;
	public Client() {
		initializeSockets();
	}

	private void initializeSockets(){
		try {
			Socket stateSocket = new Socket("localhost", 1234);
			Socket requestListenerSocket = new Socket("localhost", 1234);
			clientThread = new ClientThread(stateSocket);
			clientRequestListener = new ClientRequestListener(requestListenerSocket);
			threadPool.execute(clientThread);
			threadPool.execute(clientRequestListener);
		} catch (Exception ex) {
			logger.error(String.format("Error establishing connection to server. %s", ex.getMessage()));
		}
	}

	public State sendRequest(Request.Command command) {
		try {
			clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER));
			return clientThread.readStateFromServer();
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		} finally {
			//socket.close();
		}
	}

	public State sendRequest(Request.Command command, String playerName) {
		try {
			clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName));
			return clientThread.readStateFromServer();
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		} finally {
			//socket.close();
		}
	}

	public State sendRequest(Request.Command command, String playerName, CardInterface card, boolean uno) throws Exception {
		try {
			clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName, card, uno));
			return clientThread.readStateFromServer();
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		} finally {
			// socket.close();
		}
	}
}
