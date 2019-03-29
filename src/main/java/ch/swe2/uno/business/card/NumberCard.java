package ch.swe2.uno.business.card;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Business Class for number card (has color and number).
 */
public class NumberCard extends AbstractCard {
    // Attributes:
    private final StringProperty color;
    private final IntegerProperty number;

    /**
     * Default constructor.
     */
    public NumberCard() {
        this(null, 0);
    }

    /**
     * Constructor
     *
     * @param color
     * @param number
     */
    public NumberCard(String color, int number) {
        this.color = new SimpleStringProperty(color);
        this.number = new SimpleIntegerProperty(number);
    }

    public String getColor() {
        return color.get();
    }

    // Methods:
    public void setColor(String color) {
        this.color.set(color);
    }

    public StringProperty colorProperty() {
        return color;
    }

    public int getNumber() {
        return number.get();
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public IntegerProperty numberProperty() {
        return number;
    }
}
