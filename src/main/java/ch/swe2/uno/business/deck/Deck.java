package ch.swe2.uno.business.deck;

import ch.swe2.uno.business.card.ICard;
import ch.swe2.uno.business.card.CardFactory;
import ch.swe2.uno.business.player.IPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Business Class for a uno deck (contains 108 cards as per uno rules).
 */
public class Deck {

    private static final Logger logger = LoggerFactory.getLogger(Deck.class);
    private ArrayList<ICard> cards = new ArrayList<>(108);
    private Map<String, Color> unoColors = Map.ofEntries(
            entry("blue", Color.blue),
            entry("green", Color.green),
            entry("red", Color.red),
            entry("yellow", Color.yellow)
    );

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
     * Generate deck of cards based on game rules
     * @return void
     */
    public void initialize(){
        /**
         * Get the card factory
         */
        CardFactory cardFactory = CardFactory.getInstance();

        /**
         * Iterate through every color
         */
        for (Map.Entry<String, Color> unoColor : unoColors.entrySet()) {
            /**
             * Create one card with number: 0
             */
            cards.add(cardFactory.createCard(unoColor.getValue(), 0));

            /**
             * Create two cards with numbers: 1-9
             */
            for (int i = 1; i <= 9; i++) {
                cards.add(cardFactory.createCard(unoColor.getValue(), i));
                cards.add(cardFactory.createCard(unoColor.getValue(), i));
            }
        }

        logger.info("Generated {} cards", cards.size());
    }

    /**
     * Randomly permutes the deck using a default source of randomness.
     * Uses Fisher–Yates shuffle (https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)
     * @return void
     */
    public void shuffle(){
        Collections.shuffle(cards);
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
