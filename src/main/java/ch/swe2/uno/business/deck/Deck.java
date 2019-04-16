package ch.swe2.uno.business.deck;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.card.CardFactory;
import ch.swe2.uno.business.player.PlayerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Business Class for a uno deck (contains 108 cards as per uno rules).
 */
public class Deck {

    private static final Logger logger = LoggerFactory.getLogger(Deck.class);
    private List<CardInterface> drawPile = new ArrayList<>(108);
    private List<CardInterface> discardPile = new ArrayList<>(108);

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
        for (UnoColor unoColor: UnoColor.values()) {
            /*
              Create one card with number: 0
             */
            drawPile.add(cardFactory.create(unoColor, 0));

            /*
              Create two cards with numbers: 1-9
             */
            for (int i = 1; i <= 9; i++) {
                drawPile.add(cardFactory.create(unoColor, i));
                drawPile.add(cardFactory.create(unoColor, i));
            }
        }

        drawPile = shuffle(drawPile);

        logger.info("{} cards in draw pile", drawPile.size());
    }

    /**
     * Distribute cards to players
     * @param players ArrayList<IPlayer>
     */
    public void distribute(List<PlayerInterface> players) {
        /*
          Iterate through every player
         */
        for (PlayerInterface player : players) {
            /*
              Move 7 cards to hand of player
             */
            drawPile.subList(0, 6).stream().forEach(c -> player.addCard(c));
            drawPile.remove(drawPile.subList(0, 6));
            logger.info("Distributed {} cards to {}", player.getHand().size(), player.getName());
        }
        logger.info("{} cards in draw pile", drawPile.size());
    }

    /**
     * Randomly permutes the deck using a default source of randomness.
     * Uses Fisher–Yates shuffle (https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)
     */
    private static List<CardInterface> shuffle(List<CardInterface> cardsToShuffle){
        Collections.shuffle(cardsToShuffle);
        logger.info("Shuffled cards");
        return cardsToShuffle;
    }

    public void revealTopCard() {
        if (!drawPile.isEmpty()) {
            CardInterface topCard;
            topCard = drawPile.get(0);
            discardPile.add(topCard);
            drawPile.remove(topCard);
            logger.info("Revealed first card {} / {}",
                    getTopCardOfDiscardPile().getColor(),
                    getTopCardOfDiscardPile().getNumber());
        }
    }

    public int getDeckSize() {
        return drawPile.size();
    }

    public CardInterface getTopCardOfDiscardPile() {
        return discardPile.get(discardPile.size() - 1);
    }

    public CardInterface drawCard() {
        if(drawPile.size() >= 1) {
            CardInterface topCard = getTopCardOfDiscardPile();
            List<CardInterface> tempCards = discardPile.subList(0, discardPile.size() - 2);
            drawPile.addAll(tempCards);
            drawPile = Deck.shuffle(drawPile);
            discardPile = new ArrayList<CardInterface>();
            discardPile.add(topCard);
        }
        CardInterface card = drawPile.get(0);
        drawPile.remove(card);
        return card;
    }
}
