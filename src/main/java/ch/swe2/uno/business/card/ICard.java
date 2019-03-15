package ch.swe2.uno.business.card;

import java.awt.Color;

/**
 * Interface for cards
 */
public interface ICard {
    void setColor(Color color);
    Color getColor();
    void setValue(int value);
    int getValue();
}
