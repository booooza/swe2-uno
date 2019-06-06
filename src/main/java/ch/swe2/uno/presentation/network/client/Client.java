package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public State request(Request.Command command) throws Exception {
        try {
            socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Request(command));
            return (State) in.readObject();
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
            throw new IllegalArgumentException();
        }
        finally {
            //socket.close();
        }
    }

    public State request(Request.Command command, String playerName) {
        try {
            socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Request(command, playerName));
            return (State) in.readObject();
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
            throw new IllegalArgumentException();
        }
        finally {
            //socket.close();
        }
    }

    public State request(Request.Command command, String playerName, CardInterface card, boolean uno) throws Exception {
        try {
            socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Request(command, playerName, card, uno));
            return (State) in.readObject();
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
            throw new IllegalArgumentException();
        }
        finally {
            // socket.close();
        }
    }
}
