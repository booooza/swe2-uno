package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.util.ArrayList;

public class Player implements PlayerInterface {
    // Attributes:
    private String name;
    private ArrayList<CardInterface> hand;
    private boolean currentTurn;
    private boolean uno;

    Player(String name, ArrayList<CardInterface> hand, boolean currentTurn, boolean uno) {
        this.name = name;
        this.hand = hand;
        this.currentTurn = currentTurn;
        this.uno = uno;
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

    public void setUno(boolean uno) {
        this.uno = uno;
    }

    public boolean isUno() {
        return uno;
    }
}
