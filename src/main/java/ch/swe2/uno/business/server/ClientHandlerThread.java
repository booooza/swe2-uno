package ch.swe2.uno.business.server;

import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class ClientHandlerThread implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ClientHandlerThread.class);

	private Gson fxGson = FxGson.create();

	private Socket socket;

	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;

	private volatile boolean isRunning = true;

	private HashMap<String, ClientHandlerThread> clientInfo;
	private HashMap<String, ClientHandlerThread> clientListenerInfo;

	ClientHandlerThread(Socket socket) {
		this.socket = socket;

		this.clientInfo = MultiThreadedServer.getClientInfo();
		this.clientListenerInfo = MultiThreadedServer.getClientListenerInfo();

		logger.info("Object created on thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());
	}

	@Override
	public void run() {
		try {
			logger.info("now running on thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());

			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());

			long time = System.currentTimeMillis();
			while (isRunning) {
				if (inputStream.available() != 0) {
					continue;
				}
				Request request = (Request) inputStream.readObject();
				logger.info("Command {}", request.getCommand());
				if (request.getDirection() == Request.Direction.SERVER_TO_CLIENT) {
					break;
				}
				switch (request.getCommand()) {
					case SUBSCRIBE:
						clientListenerInfo.put(String.format("%s-%s", request.getPlayerName(), UUID.randomUUID()), this);
						break;
					case JOIN:
						outputStream.reset();
						outputStream.writeObject(MultiThreadedServer.getGame().addPlayer(request.getPlayerName()));
						clientInfo.put(String.format("%s-%s", request.getPlayerName(), UUID.randomUUID()), this);

						for (ClientHandlerThread clientHandlerThread : clientListenerInfo.values()) {
							clientHandlerThread.outputStream.reset();
							clientHandlerThread.outputStream.writeObject(new Request(Request.Command.JOINED, Request.Direction.SERVER_TO_CLIENT, MultiThreadedServer.getGame().getState()));
						}

						break;
					case START:
						if (MultiThreadedServer.getGame().getState().getPlayers() != null) {
							outputStream.reset();
							outputStream.writeObject(MultiThreadedServer.getGame().start());
							logger.info("Game initialized");
						}

						for (ClientHandlerThread clientHandlerThread : clientListenerInfo.values()) {
							clientHandlerThread.outputStream.reset();
							clientHandlerThread.outputStream.writeObject(new Request(Request.Command.STARTED, Request.Direction.SERVER_TO_CLIENT, MultiThreadedServer.getGame().getState()));
						}

						break;
					case RESTART:
						break;
					case PLAY:
						MultiThreadedServer.getGame().playCard(request.getPlayerName(), request.getCard(), request.getUno(), request.getChosenColor());
						outputStream.reset();
						outputStream.writeObject(MultiThreadedServer.getGame().getState());

						for (ClientHandlerThread clientHandlerThread : clientListenerInfo.values()) {
							clientHandlerThread.outputStream.reset();
							clientHandlerThread.outputStream.writeObject(new Request(Request.Command.PLAYED, Request.Direction.SERVER_TO_CLIENT, MultiThreadedServer.getGame().getState()));
						}
						break;
					case CHECK:
						MultiThreadedServer.getGame().check(request.getPlayerName());
						outputStream.reset();
						outputStream.writeObject(MultiThreadedServer.getGame().getState());

						for (ClientHandlerThread clientHandlerThread : clientListenerInfo.values()) {
							clientHandlerThread.outputStream.reset();
							clientHandlerThread.outputStream.writeObject(new Request(Request.Command.PLAYED, Request.Direction.SERVER_TO_CLIENT, MultiThreadedServer.getGame().getState()));
						}
						break;
					case DRAW:
						MultiThreadedServer.getGame().drawCard(request.getPlayerName());
						outputStream.reset();
						outputStream.writeObject(MultiThreadedServer.getGame().getState());
						break;
					case GETSTATE:
						outputStream.reset();
						outputStream.writeObject(MultiThreadedServer.getGame().getState());
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
			socket.close();
		} catch (Exception ex) {
			logger.error(String.format("Error in executing client's request. Details %s", ex.getMessage()));
		}
	}
}
