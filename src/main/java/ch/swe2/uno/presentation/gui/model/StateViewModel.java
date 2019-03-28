package ch.swe2.uno.presentation.gui.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class StateViewModel {
    private ObservableList<PlayerViewModel> players = FXCollections.observableArrayList();
    private NumberCardViewModel topCard;
    private StringProperty winner;
    private StringProperty message;

    /**
     * Default constructor.
     */
    public StateViewModel() {
        this(null, null, null, null);
    }

    public StateViewModel(
            ObservableList<PlayerViewModel> players, NumberCardViewModel topCard, String winner, String message) {
        this.players = new SimpleListProperty(players);
        this.topCard = topCard;
        this.winner = new SimpleStringProperty(winner);
        this.message = new SimpleStringProperty(message);
    }

    public void setPlayers(ObservableList<PlayerViewModel> players) {
        this.players = players;
    }

    public ObservableList<PlayerViewModel> getPlayers() {
        return this.players;
    }

    public ObservableList playersProperty() {
        return this.players;
    }

    public String getMessage() {
        return message.get();
    }

}
