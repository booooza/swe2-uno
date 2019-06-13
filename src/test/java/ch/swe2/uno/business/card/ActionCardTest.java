package ch.swe2.uno.business.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionCardTest {

    @Test
    @DisplayName("Create valid action card")
    void testCreationOfValidActionCard() {
        // Given
        ActionCard card = new ActionCard(CardType.WILD, UnoColor.BLACK, 88);

        // When

        // Then
        assertEquals(UnoColor.BLACK, card.getColor());
        assertEquals(UnoColor.BLACK.getColor(), "Black");
        assertEquals(88, card.getNumber());
        assertEquals(CardType.WILD, card.getType());
        assertEquals(CardType.WILD.getType(), "Wild");
    }

    @Test
    @DisplayName("Create valid action card")
    void testValidActionCardNumbers() {
        // Given
        ActionCard card = new ActionCard(CardType.WILD, UnoColor.BLACK, 88);

        // When

        // Then
        assertTrue(card.isValidCardNumber(22));
        assertTrue(card.isValidCardNumber(33));
        assertTrue(card.isValidCardNumber(44));
        assertTrue(card.isValidCardNumber(88));
        assertTrue(card.isValidCardNumber(99));
    }

    @Test
    @DisplayName("Choose color for wildcard")
    void testChooseActionCardColor() {
        // Given
        ActionCard card = new ActionCard(CardType.WILD, UnoColor.BLACK, 88);

        // When
        card.chooseColor(UnoColor.RED);

        // Then
        assertEquals(UnoColor.RED, card.getColor());
    }

    @Test
    @DisplayName("Reset color for wildcard")
    void testResetActionCardColor() {
        // Given
        ActionCard card = new ActionCard(CardType.WILD, UnoColor.BLACK, 88);

        // When
        card.chooseColor(UnoColor.RED);
        card.reset();

        // Then
        assertEquals(UnoColor.BLACK, card.getColor());
    }
}