package ch.swe2.uno.business.deck;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.player.PlayerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    public void revealTopCard() {
        if (!drawPile.isEmpty()) {
            CardInterface topCard;
            topCard = drawPile.get(0);
            discardPile.add(topCard);
            drawPile.remove(topCard);
            logger.info("Revealed first card of type: {}",
                    getTopCardOfDiscardPile().getType());
        }
    }

    public CardInterface getTopCardOfDiscardPile() {
        // Get the last card of the discard pile
        return discardPile.get(discardPile.size() - 1);
    }

    public CardInterface drawCard() {
        if(drawPile.isEmpty()) {
            logger.info("Draw Pile is exhausted, reshuffling piles...");
            // Save the top card from the discard pile for later
            CardInterface topCard = getTopCardOfDiscardPile();
            // Move discard pile to temporary list except the top card
            List<CardInterface> tempCards = discardPile.subList(0, discardPile.size() - 1);
            // Move temp card back to the drawpile
            drawPile.addAll(tempCards);
            // Shuffle the draw pule
            Collections.shuffle(drawPile);
            logger.info("Shuffled cards");
            // Create an empty discard pile
            discardPile = new ArrayList<>();
            // Put the saved top card on the discard pile
            discardPile.add(topCard);
        }
        logger.info("Drawing a card");
        CardInterface card = drawPile.get(0);
        drawPile.remove(card);
        return card;
    }

    public void addCardToDiscardPile(CardInterface card) {
        discardPile.add(card);
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public int getDiscardPileSize() {
        return discardPile.size();
    }
}
