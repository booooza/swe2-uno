package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.util.ArrayList;

public class Player implements PlayerInterface {
    // Attributes:
    private String name;
    private ArrayList<CardInterface> hand;
    private boolean currentTurn;
    private boolean uno;

    Player(String name) {
        this.name = name;
        this.hand = new ArrayList<CardInterface>();
        this.currentTurn = false;
        this.uno = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHand(ArrayList<CardInterface> hand) {
        this.hand = hand;
    }

    public ArrayList<CardInterface> getHand() {
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
