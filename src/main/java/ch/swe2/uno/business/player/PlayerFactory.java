package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;

import java.util.ArrayList;

/**
 * Class used to create people
 * Implementation of a singleton factory
 */
public class PlayerFactory {
    /**
     * Defines the private instance attribute
     */
    private static PlayerFactory ourInstance = new PlayerFactory();

    /**
     * Constructor must be private for singleton
     */
    private PlayerFactory() {}

    /**
     * Return the singleton instance
     * @return ourInstance
     */
    public static PlayerFactory getInstance() {
        return ourInstance;
    }

    /**
     * Creates a specified number of players and returns their ids
     *
     * @param number the personType can be found in person.PersonType
     * @return personId
     */
    public ArrayList<IPlayer> createPlayers(int number) {
        ArrayList<IPlayer> players = new ArrayList<IPlayer> ();
        ArrayList<ICard> hand = new ArrayList<ICard> ();

        for(int i = 0; i == number; ++i) {
            players.add(createPlayer("Name", hand));
        }

        return players;
    }

    /**
     * Create a single player
     * @return ourInstance
     */
    public IPlayer createPlayer(String name, ArrayList<ICard> hand) {
        IPlayer player = new Player(name, hand);
        return player;
    }
}
