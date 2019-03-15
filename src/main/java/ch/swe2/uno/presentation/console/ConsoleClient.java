package ch.swe2.uno.presentation.console;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.business.game.IGame;

/**
 * Class used to display data
 * Will be replaced with JavaFX GUI
 */
public class ConsoleClient {
    public static void main(String[] args) {
        /**
         * Instantiate game (singleton)
         */
        IGame game = Game.getInstance();
        game.start();
        System.out.println("Hello World");
    }
}
