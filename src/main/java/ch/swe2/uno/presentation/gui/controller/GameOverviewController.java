package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.network.client.Client;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameOverviewController {
    @FXML
    private TableView<CardInterface> playerTable;
    @FXML
    private TableColumn<CardInterface, String> playerCardTypeColumn;
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

    private ObservableList<CardInterface> observablePlayerData = FXCollections.observableArrayList();
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
        // Initialize the player table columns
        playerCardTypeColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        cellData.getValue().getType().toString()
                ));
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
            Client client = new Client();
            try {
                mainApp.setState(client.request(Request.Command.PLAY, mainApp.getPlayerName(), selectedCard));
            } catch (Exception e) {
                logger.warn("Exception: {}", e);
                throw new IllegalArgumentException();
            } finally {
                // If someone has won
                if (mainApp.getState().getWinner() != null) {
                    mainApp.showEndScreen();
                } else {
                    updateViewFromState();
                }
            }
        }
    }

    public void handleDrawButtonAction(ActionEvent event) {
        logger.info("Draw button pressed");
        Client client = new Client();
        try {
            mainApp.setState(client.request(Request.Command.DRAW, mainApp.getPlayerName()));
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
            throw new IllegalArgumentException();
        } finally {
            updateViewFromState();
        }
    }

    public void handleCheckButtonAction(ActionEvent event){
        // TODO Should only be called if player.canDraw() returns false!
        logger.info("Check button pressed");
        Client client = new Client();
        try {
            mainApp.setState(client.request(Request.Command.CHECK, mainApp.getPlayerName()));
        } catch (Exception e) {
            logger.warn("Exception: {}", e);
            throw new IllegalArgumentException();
        } finally {
            updateViewFromState();
        }
    }

    public void handleUnoButtonAction(ActionEvent event) {
        logger.info("Uno button pressed");
        updateViewFromState();
    }

    private void updateViewFromState() {
        mainApp.getState().getPlayerByName(mainApp.getPlayerName()).ifPresent(p -> {
            observablePlayerData.clear();
            observablePlayerData.addAll(p.getHand());
            playerTable.setItems(observablePlayerData);
        });
        mainApp.getState().getCurrentPlayer().ifPresent(p -> {
            currentTurn.setText(p.getName());
        });
        topCard.setText(
                mainApp.getState().getTopDiscardPileCard().getColor().toString() + " " +
                        mainApp.getState().getTopDiscardPileCard().getNumber());
        message.setText(mainApp.getState().getMessage());
        logger.info("View updated");
    }
}