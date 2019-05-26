package ch.swe2.uno.business.card;

import java.io.Serializable;

/**
 * Business Class for number card (has color and number).
 */
public class NumberCard implements CardInterface, Serializable {
    private final CardType type;
    private final UnoColor color;
    private final int number;

    /**
     * Constructor
     *
     * @param type Type of the card
     * @param color Color of the card
     * @param number Number of the card
     */
    public NumberCard(CardType type, UnoColor color, int number) {
        if (color == null || (!isValidUnoNumber(number))) {
            throw new IllegalArgumentException("color");
        }

        this.type = type;
        this.color = color;
        this.number = number;
    }

    @Override
    public CardType getType() {
        return type;
    }

    public UnoColor getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    private boolean isValidUnoNumber(int unoNumber) {
        return unoNumber < 10 && unoNumber >= 0;
    }
}
