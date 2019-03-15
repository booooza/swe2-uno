package ch.swe2.uno.business.card;

import java.awt.Color;

/**
 * Abstract Class for ICard
 * Defines attributes and default getters and setters
 */
public abstract class AbstractCard implements ICard {
    // Attributes:
    private Color color;
    private int value;

    // Methods:
    public void setColor(Color color) {
        this.color = color;
    };

    public Color getColor() {
        return this.color;
    };

    public void setValue(int value) {
        this.value = value;
    };

    public int getValue() {
        return this.value;
    };
}
