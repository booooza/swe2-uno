package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;

import java.util.ArrayList;

/**
 * Abstract Class for IPlayer
 * Defines attributes and default getters and setters
 */
public abstract class AbstractPlayer implements IPlayer {
    // Attributes:
    private String name;
    private ArrayList<ICard> hand;

    // Methods:
    public void setName(String name) {
        this.name = name;
    };

    public String getName() {
        return this.name;
    };

    public void setHand(ArrayList<ICard> hand) {
        this.hand = hand;
    };

    public ArrayList<ICard> getHand() {
        return this.hand;
    };
}
