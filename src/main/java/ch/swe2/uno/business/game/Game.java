package ch.swe2.uno.business.game;

import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * Business Class for a game
 * (rings players and deck together and defines game logic).
 */
public class Game implements GameInterface {
    private Gson fxGson = FxGson.create();
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private State gameState = new State();
    private ArrayList<PlayerInterface> playerList = new ArrayList<>(2);

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

    public void getState () {
        // Write serialized game state to file
        try (Writer writer = new FileWriter("src/main/resources/data/state.json")) {
            fxGson.toJson(gameState, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState () {
        // Read serialized game state to file
        try (Reader reader = new FileReader("src/main/resources/data/state.json")) {
            gameState = fxGson.fromJson(reader, State.class);

            logger.info("Amount of players: {}", gameState.getPlayers().size());
            gameState.getPlayers().forEach(player -> System.out.println(player.getName()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
