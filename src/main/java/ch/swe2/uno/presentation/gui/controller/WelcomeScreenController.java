package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.model.PlayerViewModel;
import ch.swe2.uno.presentation.gui.model.StateViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeScreenController {
    @FXML
    private TextField player1Name;
    @FXML
    private TextField player2Name;

    private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
    private MainApp mainApp; // Reference to the main application.
    private ObservableList<PlayerViewModel> playerList = FXCollections.observableArrayList();

    /**
     * Returns the data as an observable list of cards.
     * @return
     */
    public ObservableList<PlayerViewModel> getPlayerList() {
        return playerList;
    }


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WelcomeScreenController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void handleButtonAction(ActionEvent event) {
        logger.info("Selected Player 1: " + player1Name.getText());
        logger.info("Selected Player 2: " + player2Name.getText());

        // playerList.add(new PlayerViewModel(player1Name.getText()));
        // playerList.add(new PlayerViewModel(player2Name.getText()));

        // StateViewModel gameState = new StateViewModel(this.getPlayerList());

        // TODO: Send updated info to server

        mainApp.showGameOverview();
    }
}
