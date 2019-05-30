package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.network.client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeScreenController {
    @FXML
    private TextField playerName;

    private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
    private MainApp mainApp; // Reference to the main application.

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

    public void handleStartButtonAction() {
        logger.info("Selected Player: " + playerName.getText());
//        Client client = new Client();
//        try {
//            mainApp.setState(client.request(Request.Command.START, playerName.getText()));
//        } catch (Exception e) {
//            logger.warn("Exception: {}", e);
//            throw new IllegalArgumentException();
//        } finally {
//            mainApp.showGameOverview();
//        }

        mainApp.showGameOverview();
    }
}
