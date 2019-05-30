package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(1234)) {
            logger.info("Uno Server is Running...");
            Game game = new Game();

            Executor pool = Executors.newFixedThreadPool(200);
            pool.execute(new ClientThread(listener.accept(), game));
        }
    }
}
