package ch.swe2.uno.business.server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(1234)) {
            System.out.println("Uno Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                pool.execute(new ClientThread(listener.accept()));
                pool.execute(new ClientThread(listener.accept()));
            }
        }
    }
}
