package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {
    // Attributes:
    private String name;
    private List<CardInterface> hand;
    private boolean currentTurn;
    private boolean uno;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.currentTurn = false;
        this.uno = false;
    }

    public String getName() {
        return this.name;
    }

    public void setHand(List<CardInterface> hand) {
        this.hand = hand;
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
