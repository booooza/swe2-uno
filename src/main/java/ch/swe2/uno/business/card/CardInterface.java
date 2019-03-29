package ch.swe2.uno.business.card;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Interface for cards
 */
<<<<<<< HEAD:src/main/java/ch/swe2/uno/business/card/ICard.java
public interface ICard {
=======
public interface CardInterface {
    void setColor(String color);
>>>>>>> 906529f3bc0aec826269637ea9f7caee1538a1ca:src/main/java/ch/swe2/uno/business/card/CardInterface.java
    String getColor();

    void setColor(String color);

    StringProperty colorProperty();

    int getNumber();

    void setNumber(int number);

    IntegerProperty numberProperty();
}
