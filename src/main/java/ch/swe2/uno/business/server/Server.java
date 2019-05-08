package ch.swe2.uno.business.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(1234)) {
            logger.info("Uno Server is Running...");
            var pool = Executors.newFixedThreadPool(2);
            pool.execute(new ClientThread(listener.accept()));
            pool.execute(new ClientThread(listener.accept()));
        }
    }
}
