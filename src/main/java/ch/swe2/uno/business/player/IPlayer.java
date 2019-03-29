package ch.swe2.uno.business.player;

import javafx.beans.property.StringProperty;

/**
 * Interface for players
 */
public interface IPlayer {
    String getName();

    void setName(String name);

    StringProperty nameProperty();
}
