package ch.swe2.uno.business.card;

/**
 * Interface for cards
 */
public interface CardInterface {
    CardType getType();
    UnoColor getColor();
    int getNumber();
}
