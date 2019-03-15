package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;

import java.util.ArrayList;

/**
 * Business Class for a player.
 */
public class Player extends AbstractPlayer {

    /**
     * Constructor for player
     * @param name String
     * @param hand ArrayList<ICard>
     */
    Player(String name, ArrayList<ICard> hand) {
        this.setName(name);
        this.setHand(hand);
    }
}
