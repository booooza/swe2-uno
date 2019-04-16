package ch.swe2.uno.business.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("NumberCard")
class NumberCardTest {

    @Test
    @DisplayName("Create valid number card")
    void testCreationOfValidNumberCard() {
        // Given
        CardFactory cardFactory = CardFactory.getInstance();

        // When
        CardInterface card = cardFactory.create(UnoColor.RED, 1);

        // Then
        assertEquals(card.getColor(), UnoColor.RED);
        assertEquals(card.colorProperty().getValue(), UnoColor.RED);
        assertEquals(card.getNumber(), 1);
        assertEquals(card.numberProperty().getValue(), 1);
    }

    @Test
    @DisplayName("Create invalid number card")
    void testCreationOfInvalidNumberCard() {
        // Given
        CardFactory cardFactory = CardFactory.getInstance();

        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> { cardFactory.create(UnoColor.RED, 99); }
        );
    }
}
