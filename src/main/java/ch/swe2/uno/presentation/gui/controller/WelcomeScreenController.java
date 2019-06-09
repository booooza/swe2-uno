package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.events.EventListener;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.gui.events.RequestEventListener;
import ch.swe2.uno.presentation.network.client.Client;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeScreenController implements RequestEventHandler {
	private static Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);

	@FXML
	private TableView<PlayerInterface> playersTable;
	@FXML
	private TableColumn<PlayerInterface, Long> playerIDColumn;
	@FXML
	private TableColumn<PlayerInterface, String> playerNameColumn;
	@FXML
	private TextField playerName;
	@FXML
	private Button joinButton;
	@FXML
	private Button serverButton;

	private MainApp mainApp; // Reference to the main application.

	private ObservableList<PlayerInterface> observablePlayers = FXCollections.observableArrayList();

	private EventListener requestEventListener;

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
		// Initialize controls
		playerNameColumn.setCellValueFactory(cellData ->
				new ReadOnlyStringWrapper(
						cellData.getValue().getName()
				));
		playerIDColumn.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper(
						cellData.getValue().getId()
				));

		playerName.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
			if (playerName.isFocused() && !playerName.getText().isEmpty()) {
				playerName.selectAll();
			}
		}));
		joinButton.setDisable(false);

	}


	private void initAfterMainApp() {
		// If Server is running --> initClient
		mainApp.addRequestEventListener(this);

		if (Client.hostAvailabilityCheck()) {
			mainApp.initClient();

			serverButton.setDisable(true);
			serverButton.setText("Server Started...");
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		initAfterMainApp();
	}


	public void handleServerButtonAction() {
		try {
			mainApp.startServer();

			if (mainApp.getClient() == null) {
				mainApp.initClient();
			}
			serverButton.setDisable(true);
			serverButton.setText("Server Started...");
		} catch (Exception ex) {

		}
	}

	public void handleJoinButtonAction() {
		logger.info("Player Joined: " + playerName.getText());
		try {
			mainApp.setState(mainApp.getClient().sendRequest(Request.Command.JOIN, playerName.getText()));
			mainApp.setPlayerName(playerName.getText());
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		}
		updateViewAfterJoin();
		updatePlayersTable();
	}

	public void handleStartButtonAction() {
		logger.info("Player who started game: " + playerName.getText());
		try {
			mainApp.setPlayerName(playerName.getText());
			mainApp.getClient().sendRequest(Request.Command.START, playerName.getText());
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		}
	}

	private void updateViewAfterJoin() {
		joinButton.setDisable(true);
		joinButton.setText("Joined");
	}

	private void updatePlayersTable() {
		observablePlayers.clear();
		observablePlayers.addAll(mainApp.getState().getPlayers());
		playersTable.setItems(observablePlayers);
	}

	@Override
	public synchronized void playerJoined(State state) {
		mainApp.setState(state);
		updatePlayersTable();
	}

	@Override
	public synchronized void gameStarted(State state) {
		mainApp.setState(state);
		mainApp.showGameOverview();
	}
}
