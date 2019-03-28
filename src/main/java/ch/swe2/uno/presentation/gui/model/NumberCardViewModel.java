package ch.swe2.uno.presentation.gui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Business Class for number card (has color and number).
 */
public class NumberCardViewModel {
    private final StringProperty color;
    private final IntegerProperty number;

    /**
     * Default constructor.
     */
    public NumberCardViewModel() {
        this(null, 0);
    }

    /**
     * Constructor
     *
     * @param color
     * @param number
     */
    public NumberCardViewModel(String color, int number) {
        this.color = new SimpleStringProperty(color);
        this.number = new SimpleIntegerProperty(number);
    }

    // Methods:
    public void setColor(String color) {
        this.color.set(color);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }
}
