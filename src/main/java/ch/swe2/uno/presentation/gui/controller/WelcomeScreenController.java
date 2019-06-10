package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.network.client.Client;
import ch.swe2.uno.presentation.services.UnoService;
import io.datafx.controller.ViewController;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@ViewController(value = "/fxml/views/WelcomeScreen.fxml", title = "Welcome Screen")
public final class WelcomeScreenController implements RequestEventHandler {
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
	@FXML
	private Button startButton;

	@Inject
	private UnoService unoService;

	private MainApp mainApp; // Reference to the main application.

	private ObservableList<PlayerInterface> observablePlayers = FXCollections.observableArrayList();

	private Boolean joined = false;

	@PostConstruct
	public void init() {
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

		startButton.setOnAction(action -> handleStartButtonAction());
		joinButton.setOnAction(action -> handleJoinButtonAction());
		serverButton.setOnAction(action -> handleServerButtonAction());

		startButton.setDisable(true);
		joinButton.setDisable(false);
	}


	private void initAfterMainApp() {
		// If Server is running --> initClient
		unoService.addRequestEventListener(this);

		if (Client.hostAvailabilityCheck()) {
			unoService.initClient();

			serverButton.setDisable(true);
			serverButton.setText("Server Started...");
		}
	}

	private void handleServerButtonAction() {
		try {
			unoService.startServer();
			// TODO @Luca this needs to be a callback, server runs on new thread!
			if (unoService.getClient() == null) {
				unoService.initClient();
			}
			serverButton.setDisable(true);
			serverButton.setText("Server Started...");
		} catch (Exception ex) {
			logger.error("Error starting server", ex);
		}
	}

	private void handleJoinButtonAction() {
		logger.info("Player Joined: " + playerName.getText());
		joined = true;
		try {
			unoService.setState(unoService.getClient().sendRequest(Request.Command.JOIN, playerName.getText()));
			unoService.setPlayerName(playerName.getText());
		} catch (Exception e) {
			logger.warn("Error while joining", e);
			throw new IllegalArgumentException();
		}
	}

	private void handleStartButtonAction() {
		logger.info("Player who started game: " + playerName.getText());
		try {
			unoService.setPlayerName(playerName.getText());
			unoService.getClient().sendRequest(Request.Command.START, playerName.getText());
		} catch (Exception e) {
			logger.warn("Error while starting", e);
			throw new IllegalArgumentException();
		}

	}

	private void updateViewAfterJoin() {
		if (joined) {
			joinButton.setDisable(true);
			joinButton.setText("Joined");
			startButton.setDisable(false);
		}
	}

	private void updatePlayersTable() {
		observablePlayers.clear();
		observablePlayers.addAll(unoService.getState().getPlayers());
		playersTable.setItems(observablePlayers);
	}

	public synchronized void playerJoined(State state) {
		unoService.setState(state);
		updateViewAfterJoin();
		updatePlayersTable();
	}

	public synchronized void gameStarted(State state) {
		unoService.setState(state);
		mainApp.showGameOverview();
	}

	public synchronized void played(State state) {
		// default empty
	}
}
