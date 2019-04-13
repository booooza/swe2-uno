package ch.swe2.uno.business.deck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Deck")
public class DeckTest {
    @BeforeAll
    public static void init(){
        Deck deck = Deck.getInstance();
        deck.create();
    }

    @Test
    @DisplayName("Number of cards remaining in deck")
    public void testCreationOfDeck() {
        Deck deck = Deck.getInstance();
        assertEquals(75, deck.getDeckSize());
    }

    @Test
    @DisplayName("Top card revealed")
    public void testTopCardOfDeck() {
        Deck deck = Deck.getInstance();
        assertTrue(deck.getTopCardOfDiscardPile() != null);
    }

    @Test
    @DisplayName("Distribution of deck")
    public void testDistributionOfDeck() {
        Deck deck = Deck.getInstance();
    }

}
