package ch.swe2.uno.business.deck;

import ch.swe2.uno.business.player.IPlayer;

import java.util.ArrayList;

/**
 * Business Class for a uno deck (contains 108 cards as per uno rules).
 */
public class Deck {

    /**
     * Defines the private instance attribute
     */
    private static Deck ourInstance = new Deck();

    /**
     * Constructor must be private for singleton
     */
    private Deck(){}

    /**
     * Return the singleton instance
     * @return ourInstance
     */
    public static Deck getInstance(){
        return ourInstance;
    }

    /**
     * Generate deck from cards
     * @return void
     */
    public void initialize(){
        // TODO: Generate deck from cards
    }

    /**
     * Randomize order of the deck
     * @return void
     */
    public void shuffle(){
        // TODO: Randomize order of deck
    }

    /**
     * Distribute cards to players
     * @param players ArrayList<IPlayer>
     * @return void
     */
    public void distribute(ArrayList<IPlayer> players){
        // TODO: Distribute cards to players
    }
}
