package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;

import java.util.List;

public interface PlayerInterface {
    long getId();
    String getName();
    List<CardInterface> getHand();
    void setCurrentTurn(boolean currentTurn);
    boolean isCurrentTurn();
    void toggleCurrentTurn();
    void setUno(boolean uno);
    boolean isUno();
    boolean canDraw();
    void setCanDraw(boolean canDraw);
    void addCard(CardInterface card);
}
