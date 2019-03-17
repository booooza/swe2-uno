package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.ICard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Class used to create people
 * Implementation of a singleton factory
 */
public class PlayerFactory {
    private static final Logger logger = LoggerFactory.getLogger(PlayerFactory.class);

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
     * Create a single player
     * @return player
     */
    public IPlayer createPlayer(String name) {
        ArrayList<ICard> hand = new ArrayList<>();
        IPlayer player = new Player(name, hand);
        logger.info("Created player {}", name);
        return player;
    }
}
