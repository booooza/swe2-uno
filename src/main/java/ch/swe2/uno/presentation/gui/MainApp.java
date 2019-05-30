package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.presentation.network.client.Client;
import ch.swe2.uno.presentation.gui.controller.GameOverviewController;
import ch.swe2.uno.presentation.gui.controller.WelcomeScreenController;
import ch.swe2.uno.presentation.gui.model.NumberCardViewModel;
import ch.swe2.uno.presentation.gui.model.StateViewModel;
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

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Gson fxGson = FxGson.create();
    private StateViewModel gameState = new StateViewModel();

    /**
     * The data as an observable list of cards.
     */
    private ObservableList<NumberCardViewModel> playerData = FXCollections.observableArrayList();

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        playerData.add(new NumberCardViewModel("red", 2));
        playerData.add(new NumberCardViewModel("green", 0));
    }

    /**
     * Returns the data as an observable list of cards.
     * @return
     */
    public ObservableList<NumberCardViewModel> getPlayerData() {
        return playerData;
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

    public StateViewModel updateState() {
        try {
            Client client = new Client("localhost");
            this.gameState = fxGson.fromJson(client.getState(), StateViewModel.class);
            return this.gameState;
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
        }
        return new StateViewModel();
    }

    public StateViewModel getState() {
        return this.gameState;
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