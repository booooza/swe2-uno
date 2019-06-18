package ch.swe2.uno.business.card;

import java.io.Serializable;

public abstract class AbstractCard implements CardInterface, Serializable {
    private static final long serialVersionUID = 1L;
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
    AbstractCard(CardType type, UnoColor color, int number) {
        if (color == null || (!isValidCardNumber(number))) {
            throw new IllegalArgumentException("Illegal argument in constructor");
        }

        this.type = type;
        this.color = color;
        this.number = number;
    }

    public CardType getType() {
        return type;
    }

    public UnoColor getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    abstract boolean isValidCardNumber(int number);
}
