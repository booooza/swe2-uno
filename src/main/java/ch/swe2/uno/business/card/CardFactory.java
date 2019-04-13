package ch.swe2.uno.business.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to create cards
 * Implementation of a singleton factory
 */
public class CardFactory {
    private static final Logger logger = LoggerFactory.getLogger(CardFactory.class);

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
     * @return CardInterface card
     */
    public CardInterface create(String color, int number) {
        return new NumberCard(color, number);
    }
}
