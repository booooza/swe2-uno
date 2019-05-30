package ch.swe2.uno.presentation.network.client;

import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.MainApp;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public State request(Request.Command command, Object payload) throws Exception {
        try {
            socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Request(command, payload));
            State state = (State) in.readObject();
            out.writeObject(new Request(Request.Command.QUIT));
            return state;
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
            throw new IllegalArgumentException();
        }
        finally {
            socket.close();
        }
    }
}
