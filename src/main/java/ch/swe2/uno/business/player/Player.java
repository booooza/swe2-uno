package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface, Serializable {
    // Attributes:
    private String name;
    private final List<CardInterface> hand = new ArrayList<>();
    private boolean currentTurn;
    private boolean canEndTurn;
    private boolean uno;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<CardInterface> getHand() {
        return hand;
    }

    public void setCurrentTurn(boolean currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isCurrentTurn() {
        return currentTurn;
    }

    public void toggleCurrentTurn() {
        currentTurn = !currentTurn;
    }

    public void setCanEndTurn(boolean canEndTurn) {
        this.canEndTurn = canEndTurn;
    }

    public boolean canEndTurn() {
        return canEndTurn;
    }

    public void setUno(boolean uno) {
        this.uno = uno;
    }

    public boolean isUno() {
        return uno;
    }

    public void addCard(CardInterface card) {
        this.hand.add(card);
    }
}
