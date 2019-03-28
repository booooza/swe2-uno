package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.business.game.Game;
import ch.swe2.uno.business.game.GameInterface;
import ch.swe2.uno.presentation.gui.controller.GameOverviewController;
import ch.swe2.uno.presentation.gui.controller.WelcomeScreenController;
import ch.swe2.uno.presentation.gui.model.NumberCardViewModel;import ch.swe2.uno.presentation.gui.model.StateViewModel;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hildan.fxgson.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Gson fxGson = FxGson.create();
    private StateViewModel gameState;
    private GameInterface game = Game.getInstance();
    
    /**
     * The data as an observable list of cards.
     */
    private ObservableList<NumberCardViewModel> player1Data = FXCollections.observableArrayList();
    private ObservableList<NumberCardViewModel> player2Data = FXCollections.observableArrayList();

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        player1Data.add(new NumberCardViewModel("red", 2));
        player1Data.add(new NumberCardViewModel("green", 0));
        player2Data.add(new NumberCardViewModel("yellow", 7));
        player2Data.add(new NumberCardViewModel("blue", 3));

        /**
         * Instantiate game (singleton)
         */
        logger.info("Starting the game");
    }

    /**
     * Returns the data as an observable list of cards.
     * @return
     */
    public ObservableList<NumberCardViewModel> getPlayer1Data() {
        return player1Data;
    }

    public ObservableList<NumberCardViewModel> getPlayer2Data() {
        return player2Data;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UNO Game");

        initRootLayout();

        /**
         * Get initial game state from backend
         */
        game.getState();

        /**
         * Set initial game state in frontend
         */
        try (Reader reader = new FileReader("src/main/resources/data/state.json")) {
            gameState = fxGson.fromJson(reader, StateViewModel.class);
            if (gameState.getPlayers().size() == 0) {
                showWelcomeScreen();
            } else {
                showGameOverview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
            AnchorPane welcomeScreen = (AnchorPane) loader.load();

            // Set welcome screen into the center of root layout.
            rootLayout.setCenter(welcomeScreen);

            // Give the controller access to the main app.
            WelcomeScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
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
            AnchorPane gameOverview = (AnchorPane) loader.load();

            // Set player overview into the center of root layout.
            rootLayout.setCenter(gameOverview);

            // Give the controller access to the main app.
            GameOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}