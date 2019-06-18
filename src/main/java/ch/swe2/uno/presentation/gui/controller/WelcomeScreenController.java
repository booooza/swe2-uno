package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.network.client.Client;
import ch.swe2.uno.presentation.services.BaseService;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
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
	@FXML
	private Button clientButton;
	@FXML
	private GridPane rootGrid;
	@FXML
	private GridPane playerGrid;
	@FXML
	private ImageView logoImage;
	@FXML
	private Label playersTableLabel;
	@FXML
	private Label playerNameLabel;

	@Inject
	private BaseService baseService;

	private ObservableList<PlayerInterface> observablePlayers = FXCollections.observableArrayList();

	private Boolean joined = false;

	@PostConstruct
	public void init() {
		// Initialize controls
		joined = false;

		playerNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		playerIDColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		playerName.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
			if (playerName.isFocused() && !playerName.getText().isEmpty()) {
				playerName.selectAll();
			}
		}));

		playerName.setOnKeyPressed(ke -> {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				handleJoinButtonAction();
			}
		});

		startButton.setOnAction(action -> handleStartButtonAction());
		joinButton.setOnAction(action -> handleJoinButtonAction());
		serverButton.setOnAction(action -> handleServerButtonAction());
		clientButton.setOnAction(action -> handleClientButtonAction());

		startButton.setDisable(true);
		clientButton.setDisable(true);
		joinButton.setDisable(true);

		if (baseService.getUnoService() != null) {
			baseService.getUnoService().addRequestEventListener(this);

			if (baseService.getUnoService().getPlayerName() != null
					&& !baseService.getUnoService().getPlayerName().isEmpty()) {
				playerName.setText(baseService.getUnoService().getPlayerName());
			}
		}

		GridPane.setHalignment(logoImage, HPos.CENTER);
		GridPane.setHalignment(serverButton, HPos.CENTER);
		GridPane.setHalignment(clientButton, HPos.CENTER);
		GridPane.setHalignment(playersTableLabel, HPos.CENTER);
		GridPane.setHalignment(playerNameLabel, HPos.CENTER);

		GridPane.setHalignment(joinButton, HPos.LEFT);
		GridPane.setHalignment(startButton, HPos.RIGHT);

		checkIfServerIsAvailable();

		Platform.runLater(() -> serverButton.requestFocus());
	}

	private void checkIfServerIsAvailable() {
		if (Client.hostAvailabilityCheck()) {
			serverButton.setDisable(true);
			serverButton.setText("Server Started...");
			if(baseService.getUnoService() != null && baseService.getUnoService().getClient() != null){
				joinButton.setDisable(false);
				return;
			}
			clientButton.setDisable(false);
		}
	}

	private void initClient() {
		if (!Client.hostAvailabilityCheck()) {
			try {
				baseService.getUnoService().startService();
				Platform.runLater(() -> {
					serverButton.setDisable(true);
					serverButton.setText("Server Started...");
					clientButton.setDisable(false);
				});
			} catch (Exception ex) {
				logger.error(String.format("Error starting server %s", ex.getMessage()), ex);
			}
		} else if (baseService.getUnoService().getClient() == null) {
			Platform.runLater(() -> {
				baseService.getUnoService().initClient();
				clientButton.setDisable(true);
				clientButton.setText("Client Started...");
				joinButton.setDisable(false);
			});
		}
	}

	private void handleClientButtonAction() {
		try {
			initClient();
		} catch (Exception ex) {
			logger.error("Error starting server", ex);
		}
	}

	private void handleServerButtonAction() {
		try {
			initClient();
		} catch (Exception ex) {
			logger.error("Error starting server", ex);
		}
	}

	private void handleJoinButtonAction() {
		if (joined) {
			return;
		}
		if (!Client.hostAvailabilityCheck()) {
			return;
		}
		logger.info("Player Joined: {}", playerName.getText());
		joined = true;
		try {
			baseService.getUnoService().setState(
					baseService.getUnoService().getClient().sendRequest(Request.Command.JOIN, playerName.getText()));
			baseService.getUnoService().setPlayerName(playerName.getText());
			MainApp.getPrimaryStage().setTitle(String.format("UNO Game - %s", playerName.getText()));
		} catch (Exception e) {
			logger.warn("Error while joining", e);
		}
	}

	private void handleStartButtonAction() {
		logger.info("Player who started game: {}", playerName.getText());
		try {
			baseService.getUnoService().setPlayerName(playerName.getText());
			baseService.getUnoService().getClient().sendRequest(Request.Command.START, playerName.getText());
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
		observablePlayers.addAll(baseService.getUnoService().getState().getPlayers());
		playersTable.setItems(observablePlayers);
	}

	public synchronized void playerJoined(State state) {
		baseService.getUnoService().setState(state);
		updateViewAfterJoin();
		updatePlayersTable();
	}

	public synchronized void gameStarted(State state) {
		Platform.runLater(() -> {
			baseService.getUnoService().setState(state);
			baseService.getNavigationService().handleNavigation("GameOverview");
		});
	}

	public synchronized void played(State state) {
		// default empty
	}

	public synchronized void finished(State state) {
		// default empty
	}

	public synchronized void restarted(State state) {
		// default empty
	}
}
