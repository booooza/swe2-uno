package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.util.ArrayList;

public interface PlayerInterface {
    void setName(String name);
    String getName();
    void setHand(ArrayList<CardInterface> hand);
    ArrayList<CardInterface> getHand();
    void setCurrentTurn(boolean currentTurn);
    boolean isCurrentTurn();
    void setUno(boolean uno);
    boolean isUno();
}
