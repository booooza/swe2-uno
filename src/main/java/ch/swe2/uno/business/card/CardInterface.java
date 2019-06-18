package ch.swe2.uno.business.card;

import java.io.Serializable;

/**
 * Interface for cards
 */
public interface CardInterface extends Serializable {
    CardType getType();
    UnoColor getColor();
    int getNumber();
}
