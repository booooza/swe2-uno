package ch.swe2.uno.business.game;

import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.player.IPlayer;
import ch.swe2.uno.business.player.PlayerFactory;

import java.util.ArrayList;

/**
 * Business Class for a game
 * (rings players and deck together and defines game logic).
 */
public class Game implements IGame {
    // Attributes:
    private String direction;
    private ArrayList<IPlayer> players;

    /**
     * Defines the private instance attribute
     */
    private static Game thisInstance = new Game();

    /**
     * Constructor must be private for singleton
     */
    private Game() {
    }

    /**
     * Return the singleton instance
     * @return thisInstance
     */
    public static Game getInstance() {
        return thisInstance;
    }


    // Methods:
    public void start() {
        /**
         * Initialize deck (mixed)
         */
        Deck deck =  Deck.getInstance();

        /**
         * Shuffle (template method)
         */
        deck.shuffle();

        /**
         * Initialize players (with empty hand)
         */
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        players = playerFactory.createPlayers(2);

        /**
         * Distribute (7) cards to each player
         */
        deck.distribute(players);

        // TODO: Place cards on draw pile
        // TODO: Create discard pile
        // TODO: Draw first card
        // TODO: Start game loop
    }
}
