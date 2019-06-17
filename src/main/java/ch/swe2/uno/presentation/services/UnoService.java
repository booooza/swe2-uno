package ch.swe2.uno.presentation.services;

import ch.swe2.uno.business.server.Server;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.events.EventListener;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.gui.events.RequestEventListener;
import ch.swe2.uno.presentation.network.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnoService {
	private static Logger logger = LoggerFactory.getLogger(UnoService.class);
	private static UnoService theInstance;
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private EventListener eventListener = new RequestEventListener();
	private Client client;
	private State gameState;
	private String playerName;

	private UnoService() {
		logger.info("UnoService created");
	}

	static UnoService getInstance() {
		if (theInstance == null) {
			theInstance = new UnoService();
		}
		return theInstance;
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

	public synchronized void initClient() {
		if (this.client == null) {
			this.client = new Client(this.threadPool);
			if(this.eventListener == null){
				this.eventListener = new RequestEventListener();
			}
			this.client.setEventHandler(this.eventListener);
		}
	}

	public void stopService() {
		threadPool.shutdown();
	}

	public void addRequestEventListener(RequestEventHandler requestEventHandler) {
		this.eventListener.addEventHandler(requestEventHandler);
	}

	/**
	 * Start new Server
	 */
	public void startService() {
		String[] args = new String[]{};
		Thread serverThread = new Thread(() -> {
			try {
				Server.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(serverThread);
	}
}
