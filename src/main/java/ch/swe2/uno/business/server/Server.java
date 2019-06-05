package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class Server {
	public static int SERVER_PORT = 1234;
	protected static Game game;
	private static Logger logger = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) throws Exception {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application.properties";

		Properties appProps = new Properties();
		appProps.load(new FileInputStream(appConfigPath));
		Server.SERVER_PORT = Integer.parseInt(appProps.getProperty("SERVER_PORT"));
		new Thread(MultiThreadedServer.getInstance()).start();
		logger.info("Server started...");
	}
}
