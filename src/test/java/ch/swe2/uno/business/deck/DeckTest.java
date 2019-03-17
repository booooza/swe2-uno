package ch.swe2.uno.business.deck;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Deck")
public class DeckTest {
    @Test
    @DisplayName("Number of cards")
    public void testIfDeckContainsCorrectNumberOfCards() {
        Deck deck = Deck.getInstance();
        deck.create();
        int expected = 76;
        int actual = deck.getDeckSize();
        assertEquals(expected, actual);
    }
}
