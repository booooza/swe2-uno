package ch.swe2.uno.presentation.gui.events;

import ch.swe2.uno.business.server.Request;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RequestEventListener implements EventListener {
	private static Logger logger = LoggerFactory.getLogger(RequestEventListener.class);

	private List<RequestEventHandler> requestEventHandlers = new ArrayList<>();

	public RequestEventListener() {
		// Needs to be empty
	}

	public void addEventHandler(RequestEventHandler requestEventHandler) {
		this.requestEventHandlers.add(requestEventHandler);
	}

	public synchronized void update(Object event) {
		if (event instanceof Request) {
			switch (((Request) event).getCommand()) {
				case JOINED:
					for (RequestEventHandler reqHandler : this.requestEventHandlers) {
						Platform.runLater(() -> reqHandler.playerJoined(((Request) event).getState()));
					}
					break;
				case STARTED:
					for (RequestEventHandler reqHandler : this.requestEventHandlers) {
						Platform.runLater(() -> reqHandler.gameStarted(((Request) event).getState()));
					}
					break;
				case PLAYED:
					if (((Request) event).getState().getWinner() != null && !((Request) event).getState().getWinner().isEmpty()) {
						for (RequestEventHandler reqHandler : this.requestEventHandlers) {
							Platform.runLater(() -> reqHandler.finished(((Request) event).getState()));
						}
					} else {
						for (RequestEventHandler reqHandler : this.requestEventHandlers) {
							Platform.runLater(() -> reqHandler.played(((Request) event).getState()));
						}
					}
					break;
				case RESTART:
					for (RequestEventHandler reqHandler : this.requestEventHandlers) {
						Platform.runLater(() -> reqHandler.restarted(((Request) event).getState()));
					}
					break;
				case QUIT:
					break;
				default:
					logger.info("Unknown command {}", ((Request) event).getCommand());

			}
		}
	}
}
