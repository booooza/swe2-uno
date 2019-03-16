package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;

import java.util.ArrayList;

/**
 * Class used to create people
 * Implementation of a singleton factory
 */
public class PlayerFactory {
    /**
     * Defines the private instance attribute
     */
    private static PlayerFactory ourInstance = new PlayerFactory();

    /**
     * Constructor must be private for singleton
     */
    private PlayerFactory() {}

    /**
     * Return the singleton instance
     * @return ourInstance
     */
    public static PlayerFactory getInstance() {
        return ourInstance;
    }

    /**
     * Create a single player
     * @return player
     */
    public IPlayer createPlayer(String name) {
        ArrayList<ICard> hand = new ArrayList<ICard> ();
        IPlayer player = new Player(name, hand);
        return player;
    }
}
