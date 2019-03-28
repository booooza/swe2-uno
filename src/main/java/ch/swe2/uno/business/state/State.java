package ch.swe2.uno.business.state;

import ch.swe2.uno.business.player.Player;

import java.util.ArrayList;

public class State {
    private ArrayList<Player> players;

    public State() {
        players = new ArrayList<>(2);
    }

    public State(ArrayList<Player> players) {
        this.players = players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }
}
