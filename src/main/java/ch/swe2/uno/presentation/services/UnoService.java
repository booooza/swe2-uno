package ch.swe2.uno.presentation.services;

import ch.swe2.uno.business.server.Server;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.events.EventListener;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.gui.events.RequestEventListener;
import ch.swe2.uno.presentation.network.client.Client;
import io.datafx.controller.injection.scopes.ApplicationScoped;
import javafx.concurrent.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class UnoService {
	private static Logger logger = LoggerFactory.getLogger(UnoService.class);
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);

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
		// String classToStart = "ch.swe2.uno.business.server.Server";
		String[] args = new String[] {};
		Thread serverThread = new Thread(() -> {
			try {
				// CustomClassLoader cl = new
				// CustomClassLoader(this.getClass().getClassLoader(), c ->
				// System.out.println(c.getName()));
				// Class<?> clazz = cl.loadClass(classToStart, true);
				// Method main = clazz.getMethod("main", args.getClass());
				// main.invoke(null, (Object) args);
				Server.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(serverThread);

		try {
			//timeOutThread();
		} catch (Exception sleepEx) {
			logger.error("Error starting timeout", sleepEx);
		}
	}

	private void timeOutThread() throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(new Task());

		try {
			System.out.println("Started..");
			System.out.println(future.get(3, TimeUnit.SECONDS));
			System.out.println("Finished!");
		} catch (TimeoutException e) {
			future.cancel(true);
			System.out.println("Terminated!");
		}

		executor.shutdownNow();
	}

	class Task implements Callable<String> {
		@Override
		public String call() throws Exception {
			Thread.sleep(4000); // Just to demo a long running task of 4 seconds.
			return "Ready!";
		}
	}
}
