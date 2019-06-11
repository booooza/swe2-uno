package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.services.BaseService;
import ch.swe2.uno.presentation.services.NavigationService;
import ch.swe2.uno.presentation.services.UnoService;
import io.datafx.controller.ViewController;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@ViewController(value = "/fxml/views/GameOverview.fxml", title = "Game Overview")
public final class GameOverviewController implements RequestEventHandler {
	private static Logger logger = LoggerFactory.getLogger(GameOverviewController.class);

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

	@Inject
	private BaseService baseService;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the ch.swe2.uno.presentation.fxml file has been loaded.
	 */
	@PostConstruct
	public void init() {
		logger.info("in init start");
		// Initialize the player table columns
		playerCardTypeColumn
				.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType().toString()));
		playerCardColorColumn
				.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getColor().toString()));
		playerCardNumberColumn
				.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getNumber()));

		playerCardColorColumn.setCellFactory(column -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty); // This is mandatory

				if (item == null || empty) { // If the cell is empty
					setText(null);
					setStyle("");
				} else { // If the cell is not empty

					setText(item); // Put the String data in the cell

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
		logger.info("in init before unoservice");

		if (baseService.getUnoService() != null) {
			baseService.getUnoService().addRequestEventListener(this);
			updateViewFromState();
		}
		logger.info("in init after unoservice");
	}

	public void handlePlayButtonAction() {
		logger.info("Play button pressed");
		CardInterface selectedCard = playerTable.getSelectionModel().getSelectedItem();
		if (selectedCard != null) {
			logger.info("Selected card {} {}", selectedCard.getColor(), selectedCard.getNumber());
			try {
				if (selectedCard.getType() == CardType.WILD) {
					UnoColor chosenColor = baseService.getNavigationService().showColorDialog();
					baseService.getUnoService()
							.setState(baseService.getUnoService().getClient().sendRequest(Request.Command.PLAY,
									baseService.getUnoService().getPlayerName(), selectedCard, unoButton.isSelected(),
									chosenColor));
				} else {
					baseService.getUnoService()
							.setState(baseService.getUnoService().getClient().sendRequest(Request.Command.PLAY,
									baseService.getUnoService().getPlayerName(), selectedCard, unoButton.isSelected()));
				}
			} catch (Exception e) {
				logger.warn("Exception occurred while pressing play button", e);
				throw new IllegalArgumentException();
			}
		}
	}

	public void handleDrawButtonAction() {
		logger.info("Draw button pressed");
		try {
			baseService.getUnoService().setState(baseService.getUnoService().getClient()
					.sendRequest(Request.Command.DRAW, baseService.getUnoService().getPlayerName()));
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
			baseService.getUnoService().setState(baseService.getUnoService().getClient()
					.sendRequest(Request.Command.CHECK, baseService.getUnoService().getPlayerName()));
		} catch (Exception e) {
			logger.warn("Exception occurred while pressing play button", e);
			throw new IllegalArgumentException();
		} finally {
			updateViewFromState();
		}
	}

	private void updateViewFromState() {
		logger.info("in updateViewFromState");
		baseService.getUnoService().getState().getPlayerByName(baseService.getUnoService().getPlayerName())
				.ifPresent(p -> {
					observablePlayerData.clear();
					observablePlayerData.addAll(p.getHand());
					playerTable.setItems(observablePlayerData);
				});

		baseService.getUnoService().getState().getCurrentPlayer().ifPresent(p -> {
			currentTurn.setText(p.getName());
			if (p.getHand().size() > 1) {
				unoButton.setSelected(false);
			}
			checkButton.setDisable(p.canDraw());
			drawButton.setDisable(!p.canDraw());
		});

		topCard.setText(baseService.getUnoService().getState().getTopDiscardPileCard().getColor().toString() + " "
				+ baseService.getUnoService().getState().getTopDiscardPileCard().getNumber());

		message.setText(baseService.getUnoService().getState().getMessage());

		if (baseService.getUnoService().getState().getCurrentPlayer().isPresent()) {
			PlayerInterface currentPlayer = baseService.getUnoService().getState().getCurrentPlayer().get();
			if (currentPlayer.getName().equals(baseService.getUnoService().getPlayerName())) {
				// It my turn
				this.playButton.setDisable(false);
			} else {
				this.playButton.setDisable(true);
				this.drawButton.setDisable(true);
			}
		}
		logger.info("View updated");
	}

	public synchronized void playerJoined(State state) {
		// default empty
	}

	public synchronized void gameStarted(State state) {
		// default empty
	}

	public synchronized void played(State state) {
		baseService.getUnoService().setState(state);
		// If someone has won
		if (baseService.getUnoService().getState().getWinner() != null) {
			baseService.getNavigationService().handleNavigation("EndScreen");
		}

		updateViewFromState();
	}
}