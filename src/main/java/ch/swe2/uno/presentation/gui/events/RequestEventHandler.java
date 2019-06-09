package ch.swe2.uno.presentation.gui.events;

import ch.swe2.uno.business.state.State;

public interface RequestEventHandler {
	void playerJoined(State state);

	void gameStarted(State state);

	void played(State state);
}
