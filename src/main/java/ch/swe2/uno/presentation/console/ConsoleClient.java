package ch.swe2.uno.presentation.console;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.business.game.IGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to display data
 * Will be replaced with JavaFX GUI
 */
public class ConsoleClient {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleClient.class);

    public static void main(String[] args) {
        /**
         * Instantiate game (singleton)
         */
        logger.info("Starting the game");
        IGame game = Game.getInstance();

        /**
         * Setup game
         */
        game.setup();

    }
}
