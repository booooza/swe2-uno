package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientThread.class);
	private Socket socket;
	private Gson fxGson = FxGson.create();
	private Game game;

	ClientThread(Socket socket, Game game) {
		this.socket = socket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			long time = System.currentTimeMillis();
			logger.info("Thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());
			Request request = (Request) in.readObject();
			logger.info("Command {}", request.getCommand());

			switch (request.getCommand()) {
				case JOIN:
					game.addPlayer(request.getPlayerName());
					MultiThreadedServer.getInstance().connectClient(game);
				case START:
					if (game.getState().getPlayers() == null) {
						out.writeObject(game.initialize());
						logger.info("Game initialized");
					}
					break;
				case PLAY:
					game.playCard(request.getPlayerName(), request.getCard(), request.getUno());
					out.writeObject(game.getState());
					break;
				case CHECK:
					game.check(request.getPlayerName());
					out.writeObject(game.getState());
					break;
				case DRAW:
					game.drawCard(request.getPlayerName());
					out.writeObject(game.getState());
					break;
				case GETSTATE:
					out.writeObject(game.getState());
					break;
				default:
					logger.info("Unknown command {}", request.getCommand());
			}
			out.close();
			in.close();
			logger.info("Request processed: {}", time);
		} catch (Exception e) {
			logger.warn("Socket Exception", e);
		}
	}
}
