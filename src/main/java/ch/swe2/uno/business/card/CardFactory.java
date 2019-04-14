package ch.swe2.uno.business.card;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Arrays;

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
     * Create a single number card
     * @return CardInterface card
     */
    public CardInterface create(String color, int number) {
        if (
                Arrays.stream(UnoColors.values())
                .anyMatch(c -> c.getColor().equals(color))
        ) {
            return new NumberCard(color, number);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
