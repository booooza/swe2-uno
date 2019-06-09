package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class EndScreenController {
    private static Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
    private MainApp mainApp; // Reference to the main application.


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public EndScreenController() {

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

    public void handleExitButtonAction() {
        logger.info("Exit button pressed");
        System.exit(0);
    }
}
