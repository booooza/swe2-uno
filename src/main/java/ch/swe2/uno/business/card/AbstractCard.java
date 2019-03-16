package ch.swe2.uno.business.card;

import java.awt.Color;

/**
 * Abstract Class for ICard
 * Defines attributes and default getters and setters
 */
public abstract class AbstractCard implements ICard {
    // Attributes:
    private Color color;
    private int number;

    // Methods:
    public void setColor(Color color) {
        this.color = color;
    };

    public Color getColor() {
        return this.color;
    };

    public void setNumber(int number) {
        this.number = number;
    };

    public int getNumber() {
        return this.number;
    };
}
