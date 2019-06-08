package ch.swe2.uno.business.server;

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
	private static final Logger logger = LoggerFactory.getLogger(MultiThreadedServer.class);
	private static MultiThreadedServer theInstance;
	public ServerSocket serverSocket;
	protected int serverPort;
	protected boolean isStopped;
	protected Thread runningThread;
	protected ExecutorService threadPool =
			Executors.newFixedThreadPool(10);
	protected Game game;
	// Connection state info
	private static LinkedHashMap<String, ClientHandlerThread> clientInfo = new LinkedHashMap<String, ClientHandlerThread>();
	private static LinkedHashMap<String, ClientHandlerThread> clientListenerInfo = new LinkedHashMap<String, ClientHandlerThread>();

	public MultiThreadedServer(int port, Game game) {
		this.serverPort = port;
		this.game = game;
	}

	public static MultiThreadedServer getInstance() {
		if (theInstance == null) {
			theInstance = new MultiThreadedServer(Server.SERVER_PORT, new Game());
		}
		return theInstance;
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();

		while (!isStopped()) {
			try {
				Socket cS = serverSocket.accept();
				this.threadPool.execute(new ClientHandlerThread(cS, game));
				//new ClientHandlerThread(cS, game);
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

	public synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
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

	public static HashMap<String, ClientHandlerThread> getClientInfo() {
		return clientInfo;
	}

	public static HashMap<String, ClientHandlerThread> getClientListenerInfo() {
		return clientListenerInfo;
	}

}
