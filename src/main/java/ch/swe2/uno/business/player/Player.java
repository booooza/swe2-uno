package ch.swe2.uno.business.player;

<<<<<<< HEAD
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Abstract Class for IPlayer
 * Defines attributes and default getters and setters
 */
public class Player implements IPlayer {
=======
import ch.swe2.uno.business.card.CardInterface;

import java.util.ArrayList;

public class Player implements PlayerInterface {
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca
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

<<<<<<< HEAD
    public String getName() {
        return name.get();
    }

    // Methods:
=======
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca
    public void setName(String name) {
        this.name = name;
    }

<<<<<<< HEAD
    public StringProperty nameProperty() {
        return name;
    }
/*
    public void setHand(ArrayList<ICard> hand) {
=======
    public String getName() {
        return this.name;
    }

    public void setHand(ArrayList<CardInterface> hand) {
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca
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
