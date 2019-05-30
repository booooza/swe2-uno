package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.network.client.Client;
import ch.swe2.uno.presentation.gui.controller.GameOverviewController;
import ch.swe2.uno.presentation.gui.controller.WelcomeScreenController;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    private State gameState;
    private ObservableList<CardInterface> observablePlayerData = FXCollections.observableArrayList();

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * Constructor
     */
    public MainApp() {
        // Create a fake state for easy gui testing
        CardInterface card = new NumberCard(CardType.NUMBERCARD, UnoColor.RED, 0);
        List<PlayerInterface> players = new ArrayList<>(2);
        PlayerInterface player1 = new Player("Marc");
        player1.addCard(card);
        player1.setCurrentTurn(true);
        PlayerInterface player2 = new Player("Luca");
        player2.addCard(card);
        player2.setCurrentTurn(false);
        players.add(player1);
        players.add(player2);

        gameState = new State(players, "dummy state");
        gameState.setWinner("Winner");
        gameState.setTopDiscardPileCard(card);

        observablePlayerData.addAll(gameState.getPlayerByName("Marc").get().getHand());
    }

    /**
     * Returns the data as an list of cards.
     * @return
     */
    public ObservableList<CardInterface> getPlayerData() {
        return this.observablePlayerData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UNO Game");

        initRootLayout();

        /**
         * Instantiate game (singleton)
         */
        logger.info("Starting the game... Getting game state...");
        showWelcomeScreen();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            logger.warn("Exception: {}", e);
        }
    }

    /**
     * Shows the welcome screen inside the root layout.
     */
    public void showWelcomeScreen() {
        try {
            // Load welcome screen overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/WelcomeScreen.fxml"));
            AnchorPane welcomeScreen = loader.load();

            // Set welcome screen into the center of root layout.
            rootLayout.setCenter(welcomeScreen);

            // Give the controller access to the main app.
            WelcomeScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.warn("Exception: {}", e);
        }
    }

    /**
     * Shows the player overview inside the root layout.
     */
    public void showGameOverview() {
        try {
            // Load player overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/GameOverview.fxml"));
            AnchorPane gameOverview = loader.load();

            // Set player overview into the center of root layout.
            rootLayout.setCenter(gameOverview);

            // Give the controller access to the main app.
            GameOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.warn("Exception: {}", e);
        }
    }

    public State getState() {
        // TODO: get state from server over socket
        return gameState;
    }

    public static void main(String[] args) {
        launch(args);
    }
}