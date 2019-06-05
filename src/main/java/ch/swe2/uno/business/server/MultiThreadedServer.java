package ch.swe2.uno.business.server;

import ch.swe2.uno.business.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedServer implements Runnable {
    protected int serverPort;
    public ServerSocket serverSocket;
    protected boolean isStopped;
    protected Thread runningThread;
    protected Game game;
    private List<Thread> clients = new ArrayList();


    private static final Logger logger = LoggerFactory.getLogger(MultiThreadedServer.class);

    private static MultiThreadedServer theInstance;

    public static MultiThreadedServer getInstance(){
        if(theInstance == null) {
            theInstance = new MultiThreadedServer(Server.SERVER_PORT, new Game());
        }
        return theInstance;
    }

    public MultiThreadedServer(int port, Game game){
        this.serverPort = port;
        this.game = game;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();

        while (!MultiThreadedServer.getInstance().isStopped()) {
            try {
                new Thread(new ClientThread(MultiThreadedServer.getInstance().serverSocket.accept(), game)).start();
                //clients.add(thread);
            } catch (IOException e) {
                if (MultiThreadedServer.getInstance().isStopped()) {
                    logger.info("Server stopped...");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
        }
    }

    public void connectClient(Game game){
        while(! isStopped()){
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    logger.info("Server stopped...");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            new Thread(
                    new ClientThread(
                            clientSocket, game)
            ).start();
        }
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port", e);
        }
    }

}
