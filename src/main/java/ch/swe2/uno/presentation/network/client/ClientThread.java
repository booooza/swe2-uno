package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientThread.class);

	private final Socket clientSocket;

	private volatile ObjectInputStream inputStream = null;
	private volatile ObjectOutputStream outputStream = null;

	private Thread thread;
	private volatile boolean isRunning = true;

	ClientThread(Socket clientSocket) {
		this.clientSocket = clientSocket;

		try {
			logger.info("Thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());
			thread = new Thread(this);
			thread.start();

			// TODO @Luca always listen (Observe)!
		} catch (Exception e) {
			logger.error("Error initializing ClientThread from Socket");
		}
	}

	@Override
	public void run() {
		try {
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
/*
			long time = System.currentTimeMillis();
			while (isRunning) {
				if (inputStream.available() != 0) {
					continue;
				}
				Request request = (Request) inputStream.readObject();
				logger.info("Command {}", request.getCommand());

				switch (request.getCommand()) {
					case JOINED:
						// notify main thread about joined player
						String playerName = request.getPlayerName();

						break;
					case STARTED:
						// notify main thread to move to next stage.

						break;
					case PLAYED:
						break;
					case QUIT:
						break;
					default:
						logger.info("Unknown command {}", request.getCommand());
				}
				logger.info("Request processed: {}", time);
			}

			// close connections
			outputStream.close();
			inputStream.close();
			clientSocket.close();
 */
		} catch (Exception ex) {
			logger.error(String.format("Error in executing client's request. Details %s", ex.getMessage()));
		}
	}

	public synchronized State send(Request request) {
		try {
			outputStream.writeObject(request);
			return (State) inputStream.readObject();
		} catch (IOException e) {
			logger.error("Error sending request from client to server");
		} catch (ClassNotFoundException cnfEx) {
			logger.error("State not found, CastException");
		}
		return null;
	}
}
