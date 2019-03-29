package ch.swe2.uno.business.card;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Interface for cards
 */
public interface ICard {
    String getColor();

    void setColor(String color);

    StringProperty colorProperty();

    int getNumber();

    void setNumber(int number);

    IntegerProperty numberProperty();
}
