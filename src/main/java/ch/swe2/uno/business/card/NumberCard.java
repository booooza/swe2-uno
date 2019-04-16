package ch.swe2.uno.business.card;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Business Class for number card (has color and number).
 */
public class NumberCard implements CardInterface {
    private final StringProperty color;
    private final IntegerProperty number;

    /**
     * Constructor
     *
     * @param color Color of the card
     * @param number Number of the card
     */
    NumberCard(UnoColor color, int number) {
        this.color = new SimpleStringProperty(color.toString());
        this.number = new SimpleIntegerProperty(number);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }
}
