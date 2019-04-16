package ch.swe2.uno.business.card;

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
    public CardInterface create(UnoColor color, int number) {
        if (color == null || (!isValidUnoNumber(number))) {
            throw new IllegalArgumentException("color");
        }
        return new NumberCard(color, number);
    }    

    private boolean isValidUnoNumber(int unoNumber) {
        return unoNumber < 10 && unoNumber >= 0;
    }
}
