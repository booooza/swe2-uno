package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.business.card.ICard;
import ch.swe2.uno.business.card.NumberCard;
import ch.swe2.uno.presentation.gui.controller.GameOverviewController;
import ch.swe2.uno.presentation.gui.controller.WelcomeScreenController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of cards.
     */
    private ObservableList<ICard> player1Data = FXCollections.observableArrayList();
    private ObservableList<ICard> player2Data = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        player1Data.add(new NumberCard("red", 2));
        player1Data.add(new NumberCard("green", 0));
        player2Data.add(new NumberCard("yellow", 7));
        player2Data.add(new NumberCard("blue", 3));
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the data as an observable list of cards.
     *
     * @return
     */
    public ObservableList<ICard> getPlayer1Data() {
        return player1Data;
    }

    public ObservableList<ICard> getPlayer2Data() {
        return player2Data;
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
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}