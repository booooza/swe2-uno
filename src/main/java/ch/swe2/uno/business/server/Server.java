package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.utils.AppPropsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
	static int SERVER_PORT = 1234;
	protected static Game game;
	private static Logger logger = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) {
		Server.SERVER_PORT = AppPropsReader.readIntValueFromAppPropsBy("SERVER_PORT");

		new Thread(MultiThreadedServer.getInstance()).start();
		logger.info("Server started...");
	}
}
