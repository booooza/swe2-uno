package ch.swe2.uno.business.game;

import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.player.IPlayer;
import ch.swe2.uno.business.player.PlayerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Business Class for a game
 * (rings players and deck together and defines game logic).
 */
public class Game implements IGame {
    // Attributes:
    private String direction;
    private ArrayList<IPlayer> players = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

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

    /**
     * Prepare the game
     * Create deck, shuffle, players, distribute cards
     * @return void
     */
    public void setup() {
        /**
         * Initialize deck (mixed)
         */
        Deck deck = Deck.getInstance();
        deck.initialize();
        logger.info("Initialized deck {}", deck);

        /**
         * Shuffle deck (template method)
         */
        deck.shuffle();
        logger.info("Shuffled deck {}", deck);

        /**
         * Initialize players (with empty hand)
         */
        PlayerFactory playerFactory = PlayerFactory.getInstance();

        for(int i = 0; i < 2; ++i) {
            players.add(playerFactory.createPlayer("Player" + i));
        }

        logger.info("Initialized {} players", players.size());

        /**
         * Distribute (7) cards to each player
         */
        deck.distribute(players);
        logger.info("Distributed cards to {} players", players.size());

        // TODO: Place cards on draw pile
        // TODO: Create discard pile
        // TODO: Draw first card

    }

    public void start() {
        // TODO: Start game loop
    }

    public void end() {
        // TODO: Scoring
        // TODO: Deconstruct game
    }
}
