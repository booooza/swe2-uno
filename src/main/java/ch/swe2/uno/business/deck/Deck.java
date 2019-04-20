package ch.swe2.uno.business.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.player.PlayerInterface;

/**
 * Business Class for a uno deck (contains 108 cards as per uno rules).
 */
public class Deck {

    private static final Logger logger = LoggerFactory.getLogger(Deck.class);
    private List<CardInterface> drawPile = new ArrayList<>(108);
    private CardInterface topCard = null;
    private List<CardInterface> discardPile = new ArrayList<>(108);

    /**
     * Generate deck of cards based on game rules
     */
    public void create(){
        /*
          Iterate through every color
         */
        for (UnoColor unoColor: UnoColor.values()) {
            /*
              Create one number card with number: 0
             */
            drawPile.add(new NumberCard(CardType.NUMBERCARD, unoColor, 0));

            /*
              Create two number cards with numbers: 1-9
             */
            for (int i = 1; i <= 9; i++) {
                drawPile.add(new NumberCard(CardType.NUMBERCARD, unoColor, i));
                drawPile.add(new NumberCard(CardType.NUMBERCARD, unoColor, i));
            }
        }

        Collections.shuffle(drawPile);

        topCard = drawPile.remove(0);

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
            drawPile.subList(0, 7).forEach(player::addCard);
            drawPile.subList(0, 7).clear();
            logger.info("Distributed {} cards to {}", player.getHand().size(), player.getName());
        }
        logger.info("{} cards in draw pile", drawPile.size());
    }

    public CardInterface getTopCardOfDiscardPile() {
        // Get the last card of the discard pile
        return topCard;
    }

    public CardInterface drawCard() {
        if(drawPile.isEmpty()) {
            logger.info("Draw Pile is exhausted, reshuffling piles...");
            drawPile.addAll(discardPile);
            discardPile.clear();
            Collections.shuffle(drawPile);
        }
        logger.info("Drawing a card");
        return drawPile.remove(0);
    }

    public void addCardToDiscardPile(CardInterface card) {
        discardPile.add(topCard);
        topCard = card;
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public int getDiscardPileSize() {
        return discardPile.size();
    }
}
