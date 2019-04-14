package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardFactory;
import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientThread implements Runnable {
    Socket socket;
    PrintWriter output;
    Scanner input;
    private Gson fxGson = FxGson.create();
    private State gameState;
    private static final Logger logger = LoggerFactory.getLogger(ClientThread.class);

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // fakeState(3);
            sendState(gameState);
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
        }
    }

    /*
    private void fakeState(int selection) {
        CardFactory cardFactory = CardFactory.getInstance();

        // Create a fake top card
        CardInterface topCard = cardFactory.create("blue", 7);

        // Create a fake hand
        ArrayList<CardInterface> hand = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            hand.add(cardFactory.create("red", i));
        }

        // Create players
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        ArrayList<PlayerInterface> players = new ArrayList<>();

        switch(selection) {
            case 1:
                // Empty state
                this.gameState = new State();
                break;
            case 2:
                // Regular turn, two players, no uno, no winner, no message
                players.add(playerFactory
                        .create("Marc", hand, true, false));
                players.add(playerFactory
                        .create("Luca", hand, false, false));
                this.gameState = new State(players, topCard, "", "");
                break;
            case 3:
                // Regular turn, one player has uno, message
                players.add(playerFactory
                        .create("Marc", hand, false, true));
                players.add(playerFactory
                        .create("Luca", hand, true, false));
                this.gameState = new State(players, topCard, "", "Marc has UNO");
                break;
            case 4:
                // One player won the game
                players.add(playerFactory
                        .create("Marc", hand, false, false));
                players.add(playerFactory
                        .create("Luca", hand, true, false));
                this.gameState = new State(players, topCard, "Marc", "Marc won the game");
                break;
            default:
                this.gameState = new State();
        }
    }
    */

    private void sendState(State gameState) throws IOException {
        try (OutputStreamWriter output = new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8)) {
            output.write(fxGson.toJson(gameState));
        }
    }
}
