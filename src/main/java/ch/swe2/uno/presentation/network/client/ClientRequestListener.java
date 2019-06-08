package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static ch.swe2.uno.business.server.Request.Command.SUBSCRIBE;

public class ClientRequestListener implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientRequestListener.class);

	private final Socket clientRequestListenerSocket;

	private volatile ObjectInputStream inputStream = null;
	private volatile ObjectOutputStream outputStream = null;

	private volatile boolean isRunning = true;

	ClientRequestListener(Socket clientRequestListenerSocket) {
		this.clientRequestListenerSocket = clientRequestListenerSocket;

		try {
			logger.info("Object created on thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());
		} catch (Exception e) {
			logger.error("Error initializing ClientThread from Socket");
		}
	}

	@Override
	public synchronized void run() {
		try {
			logger.info("now running on thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());
			outputStream = new ObjectOutputStream(clientRequestListenerSocket.getOutputStream());
			inputStream = new ObjectInputStream(clientRequestListenerSocket.getInputStream());
			long time = System.currentTimeMillis();

			sendListenerConnectedMessage();

			while (isRunning) {
				if (inputStream.available() != 0) {
					continue;
				}
				Request request = readRequestFromServer();
				if (request != null) {
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
				}
				logger.info("Request processed: {}", time);
			}
			// close connections
			outputStream.close();
			inputStream.close();
			clientRequestListenerSocket.close();
		} catch (Exception ex) {
			logger.error(String.format("Error in executing client's request. Details %s", ex.getMessage()));
		}
	}

	private void sendListenerConnectedMessage() {
		try {
			outputStream.writeObject(new Request(SUBSCRIBE, Request.Direction.CLIENT_TO_SERVER));
		} catch (IOException ioEx) {
			logger.error(String.format("Error in executing client's request. Details %s", ioEx.getMessage()));
		}
	}

	private Request readRequestFromServer() {
		try {
			Object inputObject = inputStream.readObject();
			if (inputObject.getClass() == Request.class) {
				return (Request) inputObject;
			}
		} catch (ClassNotFoundException cnfE) {
			logger.error(String.format("Could not parse request from InputStream %s", cnfE.getMessage()));
		} catch (IOException e) {
			logger.error(String.format("Error sending request from client to server. %s", e.getMessage()));
		}
		return null;
	}
}
