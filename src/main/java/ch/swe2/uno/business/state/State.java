package ch.swe2.uno.business.state;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;

import java.util.ArrayList;
import java.util.Optional;

public class State {
    private ArrayList<PlayerInterface> players;
    private CardInterface topCard;
    private String winner;
    private String message;

    public State() {
    }

    public State(ArrayList<PlayerInterface> players, String message) {
        this.players = players;
        this.message = message;
    }

    public PlayerInterface getPlayerByName(String name) {
        PlayerInterface player = players.stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElse(null);
        return player;
    }

    public void toggleCurrentTurn() {
        players.stream().forEach(p -> p.toggleCurrentTurn());
    }

    public void setPlayers(ArrayList<PlayerInterface> players) {
        this.players = players;
    }

    public ArrayList<PlayerInterface> getPlayers() {
        return this.players;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setTopCard(CardInterface topCard) {
        this.topCard = topCard;
    }

    public CardInterface getTopCard() {
        return topCard;
    }
}
