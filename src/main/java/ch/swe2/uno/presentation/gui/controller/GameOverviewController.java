package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameOverviewController implements RequestEventHandler {
	private static Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
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
	@FXML
	private ToggleButton unoButton;
	@FXML
	private Button checkButton;
	@FXML
	private Button drawButton;
	@FXML
	private Button playButton;

	private ObservableList<CardInterface> observablePlayerData = FXCollections.observableArrayList();
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

		playerCardColorColumn.setCellFactory(column -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty); //This is mandatory

				if (item == null || empty) { //If the cell is empty
					setText(null);
					setStyle("");
				} else { //If the cell is not empty

					setText(item); //Put the String data in the cell

					CardInterface card = getTableView().getItems().get(getIndex());

					switch (card.getColor()) {
						case BLACK:
							setStyle("-fx-background-color: black");
							break;
						case BLUE:
							setStyle("-fx-background-color: blue");
							break;
						case RED:
							setStyle("-fx-background-color: red");
							break;
						case GREEN:
							setStyle("-fx-background-color: green");
							break;
						case YELLOW:
							setStyle("-fx-background-color: yellow; -fx-text-fill: black");
							break;
					}
				}
			}
		});
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp provide Main Application
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		initAfterMainApp();
		updateViewFromState();
	}

	private void initAfterMainApp() {
		if (mainApp.getClient() != null) {
			mainApp.addRequestEventListener(this);
		}
	}

	public void handlePlayButtonAction() {
		logger.info("Play button pressed");
		CardInterface selectedCard = playerTable.getSelectionModel().getSelectedItem();
		if (selectedCard != null) {
			logger.info("Selected card {} {}", selectedCard.getColor(), selectedCard.getNumber());
			//Client client = new Client();
			try {
				// If it's a wildcard ask for the chosen color and include in request
				if (selectedCard.getType() == CardType.WILD) {
					UnoColor chosenColor = mainApp.showColorDialog();
					mainApp.setState(
							mainApp.getClient().sendRequest(Request.Command.PLAY, mainApp.getPlayerName(), selectedCard, unoButton.isSelected(), chosenColor)
					);
				} else {
					mainApp.setState(
							mainApp.getClient().sendRequest(Request.Command.PLAY, mainApp.getPlayerName(), selectedCard, unoButton.isSelected())
					);
				}
			} catch (Exception e) {
				logger.warn("Exception occurred while pressing play button", e);
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

	public void handleDrawButtonAction() {
		logger.info("Draw button pressed");
		try {
			mainApp.setState(mainApp.getClient().sendRequest(Request.Command.DRAW, mainApp.getPlayerName()));
		} catch (Exception e) {
			logger.warn("Exception occurred while pressing play button", e);
			throw new IllegalArgumentException();
		} finally {
			updateViewFromState();
		}
	}

	public void handleCheckButtonAction() {
		// TODO Should only be called if player.canDraw() returns false!
		logger.info("Check button pressed");
		try {
			mainApp.setState(mainApp.getClient().sendRequest(Request.Command.CHECK, mainApp.getPlayerName()));
		} catch (Exception e) {
			logger.warn("Exception occurred while pressing play button", e);
			throw new IllegalArgumentException();
		} finally {
			updateViewFromState();
		}
	}

	private void updateViewFromState() {
		if (mainApp.getState().getCurrentPlayer().isPresent()) {
			PlayerInterface currentPlayer = mainApp.getState().getCurrentPlayer().get();
			if (currentPlayer.getName() == mainApp.getPlayerName()) {
				// It my turn
				this.drawButton.setDisable(false);
				this.checkButton.setDisable(false);
				this.playButton.setDisable(false);
			} else {
				this.drawButton.setDisable(true);
				this.checkButton.setDisable(true);
				this.playButton.setDisable(true);
			}
		}

		mainApp.getState().getPlayerByName(mainApp.getPlayerName()).ifPresent(p -> {
			observablePlayerData.clear();
			observablePlayerData.addAll(p.getHand());
			playerTable.setItems(observablePlayerData);
		});
		mainApp.getState().getCurrentPlayer().ifPresent(p -> {
			currentTurn.setText(p.getName());
			if (p.getHand().size() > 1) {
				unoButton.setSelected(false);
			}
			checkButton.setDisable(p.canDraw());
			drawButton.setDisable(!p.canDraw());

		});
		topCard.setText(
				mainApp.getState().getTopDiscardPileCard().getColor().toString() + " " +
						mainApp.getState().getTopDiscardPileCard().getNumber());
		message.setText(mainApp.getState().getMessage());
		logger.info("View updated");
	}

	public synchronized void playerJoined(State state) {
		// default empty
	}

	public synchronized void gameStarted(State state) {
		// default empty
	}

	public synchronized void played(State state) {
		mainApp.setState(state);
		updateViewFromState();
	}
}