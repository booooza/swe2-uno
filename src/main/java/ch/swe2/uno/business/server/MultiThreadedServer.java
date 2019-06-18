package ch.swe2.uno.business.server;

import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedServer implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(MultiThreadedServer.class);
	private static MultiThreadedServer theInstance;
	private static Game game;
	// Connection state info
	private static LinkedHashMap<String, ClientHandlerThread> clientInfo = new LinkedHashMap<String, ClientHandlerThread>();
	private static LinkedHashMap<String, ClientHandlerThread> clientListenerInfo = new LinkedHashMap<String, ClientHandlerThread>();
	private ServerSocket serverSocket;
	private int serverPort;
	private boolean isStopped;
	private ExecutorService threadPool =
			Executors.newFixedThreadPool(10);

	private MultiThreadedServer(Game game) {
		this.serverPort = Server.SERVER_PORT;
		MultiThreadedServer.game = game;
	}

	public synchronized static Game getGame() {
		return game;
	}

	static MultiThreadedServer getInstance() {
		if (theInstance == null) {
			theInstance = new MultiThreadedServer(new Game(new Deck()));
		}
		return theInstance;
	}

	static HashMap<String, ClientHandlerThread> getClientInfo() {
		return clientInfo;
	}

	static HashMap<String, ClientHandlerThread> getClientListenerInfo() {
		return clientListenerInfo;
	}

	public void run() {
		openServerSocket();

		while (!isStopped()) {
			try {
				Socket cS = serverSocket.accept();
				this.threadPool.execute(new ClientHandlerThread(cS));
			} catch (IOException e) {
				if (isStopped()) {
					logger.info("Server stopped...");
					return;
				}
				throw new RuntimeException(
						"Error accepting client connection", e);
			}
		}
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			for (ClientHandlerThread clientHandlerThread : clientListenerInfo.values()) {
				clientHandlerThread.terminate();
			}
			for (ClientHandlerThread clientHandlerThread : clientInfo.values()) {
				clientHandlerThread.terminate();
			}
			this.threadPool.shutdown();
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port", e);
		}
	}
}
