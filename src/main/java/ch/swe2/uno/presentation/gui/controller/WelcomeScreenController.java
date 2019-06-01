package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.network.client.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeScreenController {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
	@FXML
	private TextField playerName;
	@FXML
	private Button joinButton;
	private MainApp mainApp; // Reference to the main application.

	private ObservableList<PlayerInterface> observablePlayers = FXCollections.observableArrayList();

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
		playerName.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
			if (playerName.isFocused() && !playerName.getText().isEmpty()) {
				playerName.selectAll();
			}
		}));
		joinButton.setDisable(false);
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void handleJoinButtonAction() {
		logger.info("Player Joined: " + playerName.getText());
		Client client = new Client();
		try {
			mainApp.setState(client.request(Request.Command.JOIN, playerName.getText()));
			mainApp.setPlayerName(playerName.getText());
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		}
		joinButton.setDisable(false);
		joinButton.setText("Joined");
	}

	public void handleStartButtonAction() {
		logger.info("Player who started game: " + playerName.getText());
		Client client = new Client();
		try {

			//mainApp.setState(client.request(Request.Command.JOIN, playerName.getText()));
			mainApp.setPlayerName(playerName.getText());

			mainApp.setState(client.request(Request.Command.START, playerName.getText()));
		} catch (Exception e) {
			logger.warn("Exception: {}", e);
			throw new IllegalArgumentException();
		}
		mainApp.showGameOverview();
	}
}
