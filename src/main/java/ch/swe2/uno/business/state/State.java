package ch.swe2.uno.business.state;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;

import java.io.Serializable;
import java.util.Optional;
import java.util.List;

public class State implements Serializable {
    private List<PlayerInterface> players;
    private CardInterface topDiscardPileCard;
    private String winner;
    private String message;

    public State() {};

    public State(List<PlayerInterface> players, String message) {
        this.players = players;
        this.message = message;
    }

    public Optional<PlayerInterface> getPlayerByName(String name) {
        return players.stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst();
    }

    public Optional<PlayerInterface> getCurrentPlayer() {
        return players.stream()
                .filter(PlayerInterface::isCurrentTurn)
                .findFirst();
    }

    public synchronized void toggleCurrentTurn() {
        // 
        players.stream().forEach(PlayerInterface::toggleCurrentTurn);
    }

    public synchronized void setPlayers(List<PlayerInterface> players) {
        this.players = players;
    }

    public List<PlayerInterface> getPlayers() {
        return this.players;
    }

    public synchronized void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public synchronized void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public synchronized void setTopDiscardPileCard(CardInterface topDiscardPileCard) {
        this.topDiscardPileCard = topDiscardPileCard;
    }

    public CardInterface getTopDiscardPileCard() {
        return topDiscardPileCard;
    }
}
