package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.events.EventListener;
import ch.swe2.uno.utils.AppPropsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Client {
	private static int SERVER_PORT = 1234;
	private static String SERVER_ADDRESS = "localhost";
	private static Logger logger = LoggerFactory.getLogger(Client.class);
	private ClientThread clientThread = null;
	private ClientRequestListenerThread clientRequestListenerThread = null;

	public Client(ExecutorService threadPool) {
		int serverPort = AppPropsReader.readIntValueFromAppPropsBy("SERVER_PORT");
		String serverAddress = AppPropsReader.readStringValueFromAppPropsBy("SERVER_ADDRESS");
		if (serverPort != 0) {
			Client.SERVER_PORT = serverPort;
		}
		if (serverAddress != null && !(serverAddress.isEmpty())) {
			Client.SERVER_ADDRESS = serverAddress;
		}
		initializeSockets(threadPool);
	}

	public static boolean hostAvailabilityCheck() {
		try (Socket ignored = new Socket("localhost", Client.SERVER_PORT)) {
			return true;
		} catch (IOException ex) {
			/* ignore */
		}
		return false;
	}

	private void initializeSockets(ExecutorService threadPool) {
		try {
			Socket stateSocket = new Socket(Client.SERVER_ADDRESS, Client.SERVER_PORT);
			Socket requestListenerSocket = new Socket(Client.SERVER_ADDRESS, Client.SERVER_PORT);
			clientThread = new ClientThread(stateSocket);
			clientRequestListenerThread = new ClientRequestListenerThread(requestListenerSocket);
			logger.info("client threads for sockets created");
			threadPool.execute(clientThread);
			threadPool.execute(clientRequestListenerThread);
		} catch (Exception ex) {
			logger.error(String.format("Error establishing connection to server. %s", ex.getMessage()));
		}
	}

	public synchronized void setEventHandler(EventListener eventListener) {
		clientRequestListenerThread.setEventListener(eventListener);
	}

	public State sendRequest(Request.Command command, String playerName) {
		try {
			clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName));
			return clientThread.readStateFromServer();
		} catch (Exception e) {
			logger.warn(String.format("Exception: %s", e.getMessage()));
			throw new IllegalArgumentException();
		}
	}

	public State sendRequest(Request.Command command, String playerName, CardInterface card, boolean uno) {
		try {
			clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName, card, uno));
			return clientThread.readStateFromServer();
		} catch (Exception e) {
			logger.warn(String.format("Exception: %s", e.getMessage()));
			throw new IllegalArgumentException();
		}
	}

	public State sendRequest(Request.Command command, String playerName, CardInterface card, boolean uno, UnoColor chosenColor) {
		try {
			clientThread.send(new Request(command, Request.Direction.CLIENT_TO_SERVER, playerName, card, uno, chosenColor));
			return clientThread.readStateFromServer();
		} catch (Exception e) {
			logger.warn(String.format("Exception: %s", e.getMessage()));
			throw new IllegalArgumentException();
		}
	}
}
