package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class ClientHandlerThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientHandlerThread.class);

	private Socket socket;
	private Gson fxGson = FxGson.create();
	private Game game;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	// own thread
	private Thread thread;
	// check if thread is running
	private volatile boolean isRunning = true;
	// opcode
	private HashMap<String, ClientHandlerThread> clientInfo = new HashMap<String, ClientHandlerThread>();

	ClientHandlerThread(Socket socket, Game game) {
		try {
			this.socket = socket;
			this.game = game;

			this.clientInfo = MultiThreadedServer.getClientInfo();

			logger.info("Thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());

			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());

			thread = new Thread(this);
			thread.start();
		} catch (IOException e) {
			logger.error("Error initializing ClientThread from Socket");
		}
	}

	@Override
	public void run() {
		try {
			long time = System.currentTimeMillis();
			while (isRunning) {
				if (inputStream.available() != 0) {
					continue;
				}
				Request request = (Request) inputStream.readObject();
				logger.info("Command {}", request.getCommand());
				if(request.getDirection() == Request.Direction.SERVER_TO_CLIENT){
					break;
				}
				switch (request.getCommand()) {
					case JOIN:
						outputStream.writeObject(game.addPlayer(request.getPlayerName()));
						clientInfo.put(String.format("%s-%s", request.getPlayerName(), UUID.randomUUID()), this);

						for (ClientHandlerThread clientHandlerThread : clientInfo.values()) {
							clientHandlerThread.outputStream.writeObject(new Request(Request.Command.JOINED, Request.Direction.SERVER_TO_CLIENT, request.getPlayerName()));
						}

						break;
					case START:
						if (game.getState().getPlayers() != null) {
							outputStream.writeObject(game.start());
							logger.info("Game initialized");
						}
						break;
					case RESTART:
						break;
					case PLAY:
						game.playCard(request.getPlayerName(), request.getCard(), request.getUno());
						outputStream.writeObject(game.getState());
						break;
					case CHECK:
						game.check(request.getPlayerName());
						outputStream.writeObject(game.getState());
						break;
					case DRAW:
						game.drawCard(request.getPlayerName());
						outputStream.writeObject(game.getState());
						break;
					case GETSTATE:
						outputStream.writeObject(game.getState());
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
