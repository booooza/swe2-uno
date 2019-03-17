package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Abstract Class for IPlayer
 * Defines attributes and default getters and setters
 */
public class Player implements IPlayer {
    // Attributes:
    private final StringProperty name;
    // private final ListProperty hand;

    /**
     * Default constructor.
     */
    public Player() {
        this(null);
    }

    /**
     * Constructor
     *
     * @param name
     */
    public Player(String name) {
        this.name = new SimpleStringProperty(name);
    }

    // Methods:
    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }
/*
    public void setHand(ArrayList<ICard> hand) {
        this.hand = hand;
    }

    public ArrayList<ICard> getHand() {
        return this.hand;
    }

    public void draw(ICard card) {
        hand.add(card);
    }

    public int getHandSize() {
        return this.hand.size();
    }
*/
}
