package ch.swe2.uno.business.player;

import javafx.beans.property.StringProperty;

/**
 * Interface for players
 */
public interface IPlayer {
    void setName(String name);
    String getName();
    StringProperty nameProperty();
}
