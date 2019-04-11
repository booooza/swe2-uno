package ch.swe2.uno.presentation.network.client;

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
    private Gson fxGson = FxGson.create();
    JsonParser parser = new JsonParser();
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
    }

    public JsonElement getState() throws Exception {
        try {
            JsonElement gameState = parser.parse(in);
            fxGson.toJson(gameState);
            logger.info("Current game state received from server: {}", gameState.toString());
            return gameState;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            socket.close();
        }
        return new JsonObject();
    }
}
