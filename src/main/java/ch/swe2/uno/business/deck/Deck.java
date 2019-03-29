package ch.swe2.uno.business.deck;

<<<<<<< HEAD
import ch.swe2.uno.business.card.CardFactory;
import ch.swe2.uno.business.card.ICard;
import ch.swe2.uno.business.player.IPlayer;
=======
import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardFactory;
import ch.swe2.uno.business.player.PlayerInterface;
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Business Class for a uno deck (contains 108 cards as per uno rules).
 */
public class Deck {

    private static final Logger logger = LoggerFactory.getLogger(Deck.class);
<<<<<<< HEAD
=======
    private ArrayList<CardInterface> drawPile = new ArrayList<>(108);
    private ArrayList<CardInterface> discardPile = new ArrayList<>(108);
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca
    private static final ArrayList<String> unoColors = new ArrayList<>(4);
    /**
     * Defines the private instance attribute
     */
    private static Deck ourInstance = new Deck();
    private ArrayList<ICard> drawPile = new ArrayList<>(108);
    private ArrayList<ICard> discardPile = new ArrayList<>(108);

    /**
     * Constructor must be private for singleton
     */
    private Deck() {
    }

    /**
     * Return the singleton instance
     *
     * @return ourInstance
     */
    public static Deck getInstance() {
        return ourInstance;
    }

    /**
     * Generate deck of cards based on game rules
     */
    public void create() {
        /*
          Get the card factory
         */
        CardFactory cardFactory = CardFactory.getInstance();

        /*
          Iterate through every color
         */
        unoColors.add("red");
        unoColors.add("blue");
        unoColors.add("yellow");
        unoColors.add("green");

        for (String unoColor : unoColors) {
            /*
              Create one card with number: 0
             */
            drawPile.add(cardFactory.createCard(unoColor, 0));

            /*
              Create two cards with numbers: 1-9
             */
            for (int i = 1; i <= 9; i++) {
                drawPile.add(cardFactory.createCard(unoColor, i));
                drawPile.add(cardFactory.createCard(unoColor, i));
            }
        }

        logger.info("Generated {} cards", this.getDeckSize());
    }

    /**
     * Randomly permutes the deck using a default source of randomness.
     * Uses Fisher–Yates shuffle (https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)
     */
    public void shuffle(int times) {
        for (int i = 0; i < times; i++) {
            Collections.shuffle(drawPile);
        }
        logger.info("Shuffled deck {} times", times);
    }

    /**
     * Distribute cards to players
     *
     * @param players ArrayList<IPlayer>
     */
<<<<<<< HEAD
    public void distribute(ArrayList<IPlayer> players) {
=======
    public void distribute(ArrayList<PlayerInterface> players){
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca
        /*
          Iterate through every player
         */
        for (PlayerInterface player : players) {
            /*
              Move 7 cards to hand of player
             */
            for (int i = 0; i < 7; i++) {
                // player.draw(drawPile.get(i));
                drawPile.remove(i);
            }
            // logger.info("Distributed {} cards to {}", player.getHandSize(), player.getName());
        }
        logger.info("{} cards remaining in deck", this.getDeckSize());
    }

    public void revealTopCard() {
        if (!drawPile.isEmpty() && drawPile.size() > 0) {
            CardInterface topCard;
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

    public CardInterface getTopCardOfDiscardPile() {
        return discardPile.get(0);
    }
}
