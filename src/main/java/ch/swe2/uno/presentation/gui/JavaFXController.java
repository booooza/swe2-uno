package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.business.game.IGame;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFXController {
    private static final Logger logger = LoggerFactory.getLogger(JavaFXClient.class);

    public void handleButtonAction(ActionEvent event) {
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
