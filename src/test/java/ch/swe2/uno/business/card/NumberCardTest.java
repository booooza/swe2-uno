package ch.swe2.uno.business.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("NumberCard")
public class NumberCardTest {
    @Test
    @DisplayName("Creation of number card")
    public void testCreationOfNumberCard() {
        CardFactory cardFactory = CardFactory.getInstance();
        CardInterface card = cardFactory.create("red", 2);
        assertEquals("red", card.getColor());
        assertEquals(2, card.getNumber());
    }
}
