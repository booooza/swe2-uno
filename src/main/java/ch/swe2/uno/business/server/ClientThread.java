package ch.swe2.uno.business.server;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientThread implements Runnable {
    Socket socket;
    private Gson fxGson = FxGson.create();
    private State gameState;
    private static final Logger logger = LoggerFactory.getLogger(ClientThread.class);

    public ClientThread(Socket socket) {
        this.socket = socket;
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
                        out.writeObject("Hello " + getPayloadSync(request.getPayload()));
                        break;
                    case PLAY:
                        //game.play(request.getCard());
                        logger.info("Command {}", request.getCommand());
                        break;
                    case DRAW:
                        logger.info("Command {}", request.getCommand());
                        break;
                    case GETSTATE:
                        logger.info("Command {}", request.getCommand());
                        out.writeObject(request.getState());
                        break;
                    default:
                        logger.info("Unknown command {}", request.getCommand());
                }
                request = (Request) in.readObject();
            }

        } catch (Exception e) {
            logger.warn("Exception", e);
        }
    }

    // Locked method (can only be used by one thread at a time)
    // TODO: move to class containing the shared state (the lock is on the )
    private synchronized Object getPayloadSync(Object payload) {
        return payload;
    }

//
//    private void sendState(State gameState) throws IOException {
//        try (OutputStreamWriter output = new OutputStreamWriter(
//                socket.getOutputStream(), StandardCharsets.UTF_8)) {
//            output.write(fxGson.toJson(gameState));
//        }

}
