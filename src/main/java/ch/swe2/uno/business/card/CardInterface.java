package ch.swe2.uno.business.card;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Interface for cards
 */
public interface CardInterface {
    void setColor(String color);
    String getColor();
    StringProperty colorProperty();
    void setNumber(int number);
    int getNumber();
    IntegerProperty numberProperty();
}
