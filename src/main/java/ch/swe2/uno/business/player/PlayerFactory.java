package ch.swe2.uno.business.player;

import ch.swe2.uno.business.card.CardInterface;
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
    private PlayerFactory() {
    }

    /**
     * Return the singleton instance
     *
     * @return ourInstance
     */
    public static PlayerFactory getInstance() {
        return ourInstance;
    }

    /**
     * Create a single player
     *
     * @return player
     */
    public PlayerInterface createPlayer(String name, ArrayList<CardInterface> hand, boolean currentTurn, boolean uno) {
        PlayerInterface player = new Player(name, hand, currentTurn, uno);
        logger.info("Created player {}", name);
        return player;
    }
}
