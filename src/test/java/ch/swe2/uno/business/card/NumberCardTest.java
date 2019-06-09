package ch.swe2.uno.business.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("NumberCard")
class NumberCardTest {

	@Test
	@DisplayName("Create valid number card")
	void testCreationOfValidNumberCard() {
		// Given
		NumberCard card = new NumberCard(CardType.NUMBERCARD, UnoColor.RED, 1);

		// When

		// Then
		assertEquals(UnoColor.RED, card.getColor());
		assertEquals(UnoColor.RED.getColor(), "Red");
		assertEquals(1, card.getNumber());
		assertEquals(CardType.NUMBERCARD, card.getType());
		assertEquals(CardType.NUMBERCARD.getType(), "Number");
	}

	@Test
	@DisplayName("Create invalid number card")
	void testCreationOfInvalidNumberCard() {
		// Given

		// When

		// Then
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					new NumberCard(CardType.NUMBERCARD, UnoColor.RED, 99);
				}
		);
	}
}
