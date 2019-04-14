package ch.swe2.uno.business.state;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;

import java.util.List;

public class State {
    private List<PlayerInterface> players;
    private CardInterface topCard;
    private String winner;
    private String message;

    public State() {
    }

    public State(List<PlayerInterface> players, String message) {
        this.players = players;
        this.message = message;
    }

    public PlayerInterface getPlayerByName(String name) {
        return players.stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElse(null);
    }

    public void toggleCurrentTurn() {
        players.stream().forEach(PlayerInterface::toggleCurrentTurn);
    }

    public void setPlayers(List<PlayerInterface> players) {
        this.players = players;
    }

    public List<PlayerInterface> getPlayers() {
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
