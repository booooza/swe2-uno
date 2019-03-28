package ch.swe2.uno.presentation.gui.model;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlayerViewModel {
    private SimpleStringProperty name;
    private ObservableList<NumberCardViewModel> hand = FXCollections.observableArrayList();
    private SimpleBooleanProperty currentTurn;
    private SimpleBooleanProperty uno;

    /**
     * Default constructor.
     */
    public PlayerViewModel() {
        this(null, null, false, false);
    }

    /**
     * Constructor with some initial data.
     *
     * @param name
     */
    public PlayerViewModel(String name, ObservableList<NumberCardViewModel> hand, boolean currentTurn, boolean uno) {
        this.name = new SimpleStringProperty(name);
        this.hand = new SimpleListProperty(hand);
        //hand.forEach(card -> this.hand.add(card));
        this.currentTurn = new SimpleBooleanProperty(currentTurn);
        this.currentTurn = new SimpleBooleanProperty(uno);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<NumberCardViewModel> getHand() {
        return hand;
    }

    public boolean isUno() {
        return uno.get();
    }

    public boolean isCurrentTurn() {
        return currentTurn.get();
    }
}
