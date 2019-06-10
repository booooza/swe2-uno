package ch.swe2.uno.presentation.services;

import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.events.EventListener;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.gui.events.RequestEventListener;
import ch.swe2.uno.presentation.network.client.Client;
import ch.swe2.uno.utils.CustomClassLoader;
import io.datafx.controller.injection.scopes.FlowScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@FlowScoped
public class UnoService {
	private static Logger logger = LoggerFactory.getLogger(UnoService.class);
	private ExecutorService threadPool =
			Executors.newFixedThreadPool(10);

	private EventListener eventListener = new RequestEventListener();

	private Client client;
	private State gameState;
	private String playerName;

	public UnoService() {
		logger.info("UnoService created");
	}

	public State getState() {
		return gameState;
	}

	public void setState(State state) {
		this.gameState = state;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Client getClient() {
		return this.client;
	}

	public void initClient() {
		client = new Client(this.threadPool);
		client.setEventHandler(this.eventListener);
	}

	public void addRequestEventListener(RequestEventHandler requestEventHandler) {
		this.eventListener.addEventHandler(requestEventHandler);
	}

	/**
	 * Start new Server
	 */
	public void startServer() {
		String classToStart = "ch.swe2.uno.business.server.Server";
		String[] args = new String[]{};

		// start a new thread
		Thread serverThread = new Thread(() -> {
			try {
				// create the custom class loader
				//ClassLoader cl = new MainApp.CustomClassLoader();
				CustomClassLoader cl = new CustomClassLoader(this.getClass().getClassLoader(), c -> System.out.println(c.getName()));
				// load the class
				Class<?> clazz = cl.loadClass(classToStart, true);

				// get the main method
				Method main = clazz.getMethod("main", args.getClass());

				// and invoke it
				main.invoke(null, (Object) args);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(serverThread);
	}

}
