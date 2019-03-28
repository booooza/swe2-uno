package ch.swe2.uno.presentation.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerViewModel {
    private final SimpleStringProperty name;

    /**
     * Default constructor.
     */
    public PlayerViewModel() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param name
     */
    public PlayerViewModel(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty name() {
        return name;
    }
}
