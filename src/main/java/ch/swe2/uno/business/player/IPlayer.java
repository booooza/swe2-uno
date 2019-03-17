package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;

import java.util.ArrayList;

/**
 * Interface for players
 */
public interface IPlayer {
    void setName(String name);
    String getName();
    void setHand(ArrayList<ICard> hand);
    ArrayList<ICard> getHand();
    void draw(ICard card);
    int getHandSize();
}
