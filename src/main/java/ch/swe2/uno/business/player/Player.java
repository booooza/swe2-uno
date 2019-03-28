package ch.swe2.uno.business.player;

import java.io.Serializable;

public class Player implements PlayerInterface, Serializable {
    // Attributes:
    private String name;

    Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
