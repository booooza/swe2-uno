package ch.swe2.uno.presentation.gui.events;

public interface EventListener {
	void addEventHandler(RequestEventHandler requestEventHandler);

	void update(Object event);
}
