package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.business.state.State;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;
    private Gson fxGson = FxGson.create();
    private Game game;
    private static final Logger logger = LoggerFactory.getLogger(ClientThread.class);

    ClientThread(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        try (
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            logger.info("Thread \"{}\" state {}", Thread.currentThread().getName(), Thread.currentThread().getState());
            Request request = (Request) in.readObject();
            while (!Request.Command.QUIT.equals(request.getCommand())) {
                switch (request.getCommand()) {
                    case START:
                        logger.info("Command {}", request.getCommand());
                        game.addPlayer(request.getPlayerName());
                        if (game.getState().getPlayers() == null) {
                            game.initialize();
                            logger.info("Game initialized");
                        }
                        out.writeObject("Hello " + request.getPlayerName());
                        break;
                    case PLAY:
                        logger.info("Command {}", request.getCommand());
                        game.playCard(request.getPlayerName(), request.getCard());
                        out.writeObject(game.getState());
                        break;
                    case DRAW:
                        logger.info("Command {}", request.getCommand());
                        game.drawCard(request.getPlayerName());
                        out.writeObject(game.getState());
                        break;
                    case GETSTATE:
                        logger.info("Command {}", request.getCommand());
                        out.writeObject(game.getState());
                        break;
                    default:
                        logger.info("Unknown command {}", request.getCommand());
                }
                // request = (Request) in.readObject();
            }

        } catch (Exception e) {
            logger.warn("Socket Exception", e);
        }
    }
}
