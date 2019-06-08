package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.controller.ColorDialogController;
import ch.swe2.uno.presentation.gui.controller.EndScreenController;
import ch.swe2.uno.presentation.gui.controller.GameOverviewController;
import ch.swe2.uno.presentation.gui.controller.WelcomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private State gameState;
    private String playerName;
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * Constructor
     */
    public MainApp() {

    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UNO Game");

        initRootLayout();
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
            // Load welcome screen.
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
            // Load game overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/GameOverview.fxml"));
            AnchorPane gameOverview = loader.load();

            // Set game overview into the center of root layout.
            rootLayout.setCenter(gameOverview);

            // Give the controller access to the main app.
            GameOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.warn("Exception: {}", e);
        }
    }

    /**
     * Opens a dialog to choose color for the specified card.
     */
    public UnoColor showColorDialog(CardInterface card) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ColorDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Choose color");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            ColorDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            return controller.getChosenColor();
        } catch (IOException e) {
            e.printStackTrace();
            return UnoColor.BLACK;
        }
    }

    public void showEndScreen() {
        try {
            // Load end screen.
            FXMLLoader loader = new FXMLLoader();
            if (gameState.getWinner().equals("Bot")) {
                loader.setLocation(MainApp.class.getResource("/view/FailScreen.fxml"));
            } else {
                loader.setLocation(MainApp.class.getResource("/view/SuccessScreen.fxml"));
            }
            AnchorPane endScreen = loader.load();

            // Set welcome screen into the center of root layout.
            rootLayout.setCenter(endScreen);

            // Give the controller access to the main app.
            EndScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.warn("Exception: {}", e);
        }
    }

    public void setState(State state) {
        this.gameState = state;
    }

    public State getState() {
        return gameState;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public static void main(String[] args) {
        launch(args);
    }
}