package ch.swe2.uno.presentation.gui.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class StateViewModel {
    private ListProperty<PlayerViewModel> players;

    /**
     * Default constructor.
     */
    public StateViewModel() {
        this(null);
    }

    public StateViewModel(ObservableList<PlayerViewModel> players) {
        this.players = new SimpleListProperty(players);
    }

    public void setPlayers(SimpleListProperty<PlayerViewModel> players) {
        this.players = players;
    }

    public ListProperty<PlayerViewModel> getPlayers() {
        return this.players;
    }

    public ListProperty playersProperty() {
        return this.players;
    }
}
