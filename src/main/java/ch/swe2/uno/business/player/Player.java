package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface, Serializable {
    private static final long serialVersionUID = 1L;
    // Attributes:
    private long id;
    private String name;
    private final List<CardInterface> hand = new ArrayList<>();
    private boolean currentTurn;
    private boolean canDraw = true;
    private boolean uno;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return this.id;
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

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    public boolean canDraw() {
        return canDraw;
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
