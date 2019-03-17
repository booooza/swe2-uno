package ch.swe2.uno.business.card;

import java.awt.*;

/**
 * Class used to create cards
 * Implementation of a singleton factory
 */
public class CardFactory {
    /**
     * Defines the private instance attribute
     */
    private static CardFactory ourInstance = new CardFactory();

    /**
     * Constructor must be private for singleton
     */
    private CardFactory() {}

    /**
     * Return the singleton instance
     * @return ourInstance
     */
    public static CardFactory getInstance() {
        return ourInstance;
    }

    /**
     * Create a single card
     * @return ICard card
     */
    public ICard createCard(Color color, int number) {
        return new NumberCard(color, number);
    }


}
