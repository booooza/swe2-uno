package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.business.card.UnoColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Request Test")
class RequestTest {

    @Test
    @DisplayName("Get Command Test")
    void getCommand() {
        // Given
        Request request = new Request(Request.Command.START);

        // When

        // Then
        assertEquals(Request.Command.START, request.getCommand());
    }

    @Test
    void getPayload() {
        // Given
        String payload = "Payload";
        Request request = new Request(Request.Command.START, payload);

        // When

        // Then
        assertEquals("Payload", request.getPayload());
    }

    @Test
    void getCardWithValidCard() {
        // Given
        CardInterface card = new NumberCard(CardType.NUMBERCARD, UnoColor.RED, 0);
        Request request = new Request(Request.Command.PLAY, card);

        // When

        // Then
        assertEquals(card, request.getCard());
    }

    @Test
    void getCardWithInvalidCard() {
        // Given
        String noCard = "Not a Card";
        Request request = new Request(Request.Command.PLAY, noCard);

        // When

        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> { request.getCard(); }
        );
    }
}