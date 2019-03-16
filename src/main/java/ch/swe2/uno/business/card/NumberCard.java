package ch.swe2.uno.business.card;

import java.awt.Color;

/**
 * Business Class for number card (has color and number).
 */
public class NumberCard extends AbstractCard {

    /**
     * Constructor for NumberCard
     * @param color Color
     * @param number int
     */
    public NumberCard(Color color, int number) {
        this.setColor(color);
        this.setNumber(number);
    }
}
