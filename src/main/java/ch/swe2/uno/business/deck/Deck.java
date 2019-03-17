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
    private ArrayList<ICard> drawPile = new ArrayList<>(108);
    private ArrayList<ICard> discardPile = new ArrayList<>(108);
    private static final Map<String, Color> unoColors = Map.ofEntries(
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
     */
    public void create(){
        /*
          Get the card factory
         */
        CardFactory cardFactory = CardFactory.getInstance();

        /*
          Iterate through every color
         */
        for (Map.Entry<String, Color> unoColor : unoColors.entrySet()) {
            /*
              Create one card with number: 0
             */
            drawPile.add(cardFactory.createCard(unoColor.getValue(), 0));

            /*
              Create two cards with numbers: 1-9
             */
            for (int i = 1; i <= 9; i++) {
                drawPile.add(cardFactory.createCard(unoColor.getValue(), i));
                drawPile.add(cardFactory.createCard(unoColor.getValue(), i));
            }
        }

        logger.info("Generated {} cards", this.getDeckSize());
    }

    /**
     * Randomly permutes the deck using a default source of randomness.
     * Uses Fisher–Yates shuffle (https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)
     */
    public void shuffle(int times){
        for (int i = 0; i < times; i++) {
            Collections.shuffle(drawPile);
        }
        logger.info("Shuffled deck {} times", times);
    }

    /**
     * Distribute cards to players
     * @param players ArrayList<IPlayer>
     */
    public void distribute(ArrayList<IPlayer> players){
        /*
          Iterate through every player
         */
        for (IPlayer player : players) {
            /*
              Move 7 cards to hand of player
             */
            for (int i = 0; i < 7; i++) {
                player.draw(drawPile.get(i));
                drawPile.remove(i);
            }
            logger.info("Distributed {} cards to {}", player.getHandSize(), player.getName());
        }
        logger.info("{} cards remaining in deck", this.getDeckSize());
    }

    public void revealTopCard() {
        if (!drawPile.isEmpty() && drawPile.size() > 0) {
            ICard topCard;
            topCard = drawPile.get(0);
            discardPile.add(topCard);
            drawPile.remove(topCard);
            logger.info("Revealed first card {} / {}",
                    this.getTopCardOfDiscardPile().getColor(),
                    this.getTopCardOfDiscardPile().getNumber());
        }
    }

    public int getDeckSize() {
        return drawPile.size();
    }

    public ICard getTopCardOfDiscardPile() {
        return discardPile.get(0);
    }
}
