package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeScreenController {
    private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
    // Reference to the main application.
    private MainApp mainApp;

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
        logger.info("Clicked");
        mainApp.showGameOverview();
    }
}
