package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.model.NumberCardViewModel;
import ch.swe2.uno.presentation.gui.model.PlayerViewModel;
import ch.swe2.uno.presentation.gui.model.StateViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GameOverviewController {
    @FXML
    private TableView<NumberCardViewModel> playerTable;
    @FXML
    private TableColumn<NumberCardViewModel, String> playerCardColorColumn;
    @FXML
    private TableColumn<NumberCardViewModel, Number> playerCardNumberColumn;
    @FXML
    private Label playerName;
    @FXML
    private Label playerUno;
    @FXML
    private Label currentTurn;
    @FXML
    private Label topCard;
    @FXML
    private Label message;

    private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
    private MainApp mainApp; // Reference to the main application.

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GameOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        playerCardColorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        playerCardNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable data to the view
        playerTable.setItems(mainApp.getPlayerData());
        topCard.setText("Red 7");
        message.setText("Game started");
    }

    public void handlePlayButtonAction(ActionEvent event) {
        logger.info("Play button pressed");
        NumberCardViewModel selectedCard = playerTable.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            logger.info("Selected card {} {}", selectedCard.getColor(), selectedCard.getNumber());
        }
    }

    public void handleDrawButtonAction(ActionEvent event) {
        logger.info("Draw button pressed");
    }

    public void handleUnoButtonAction(ActionEvent event) {
        logger.info("Uno button pressed");
    }
}