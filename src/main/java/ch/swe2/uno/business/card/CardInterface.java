package ch.swe2.uno.business.card;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Interface for cards
 */
public interface CardInterface {
    String getColor();
    StringProperty colorProperty();
    int getNumber();
    IntegerProperty numberProperty();
}
