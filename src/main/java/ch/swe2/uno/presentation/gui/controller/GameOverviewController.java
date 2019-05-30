package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.presentation.gui.MainApp;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class GameOverviewController {
    @FXML
    private TableView<CardInterface> playerTable;
    @FXML
    private TableColumn<CardInterface, String> playerCardColorColumn;
    @FXML
    private TableColumn<CardInterface, Number> playerCardNumberColumn;
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
        playerCardColorColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        cellData.getValue().getColor().toString()
                ));
        playerCardNumberColumn.setCellValueFactory(cellData ->
                new ReadOnlyIntegerWrapper(
                        cellData.getValue().getNumber()
                ));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        updateViewFromState();
    }

    public void handlePlayButtonAction() {
        logger.info("Play button pressed");
        CardInterface selectedCard = playerTable.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            logger.info("Selected card {} {}", selectedCard.getColor(), selectedCard.getNumber());
        }
        updateViewFromState();
    }

    public void handleDrawButtonAction(ActionEvent event) {
        logger.info("Draw button pressed");
        updateViewFromState();
    }

    public void handleUnoButtonAction(ActionEvent event) {
        logger.info("Uno button pressed");
        updateViewFromState();
    }

    private void updateViewFromState() {
        // Add observable data to the view
        playerTable.setItems(mainApp.getPlayerData());
        topCard.setText(
                mainApp.getState().getTopDiscardPileCard().getColor().toString() + " " +
                        mainApp.getState().getTopDiscardPileCard().getNumber());
        message.setText(mainApp.getState().getMessage());
        currentTurn.setText(mainApp.getState().getCurrentPlayer().get().getName());
        logger.info("View updated");
    }
}