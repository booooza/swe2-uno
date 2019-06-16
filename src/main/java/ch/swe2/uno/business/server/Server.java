package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.utils.AppPropsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
	protected static Game game;
	static int SERVER_PORT = 1234;
	private static Logger logger = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) {
		int serverPort = AppPropsReader.readIntValueFromAppPropsBy("SERVER_PORT");
		if (serverPort != 0) {
			Server.SERVER_PORT = serverPort;
		}
		new Thread(MultiThreadedServer.getInstance()).start();
		logger.info("Server started...");
	}
}
