package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.business.server.Request;
import ch.swe2.uno.business.state.State;
import ch.swe2.uno.presentation.gui.events.RequestEventHandler;
import ch.swe2.uno.presentation.services.BaseService;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@ViewController(value = "/fxml/views/EndScreen.fxml", title = "Game Overview")
public final class EndScreenController implements RequestEventHandler {
	private static Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);

	@FXML
	private Button endButton;

	@FXML
	private Button restartButton;

	@FXML
	private ImageView imgView;

	@FXML
	private Label message;

	@Inject
	private BaseService baseService;

	@PostConstruct
	public void init() {
		endButton.setOnAction(action -> handleExitButtonAction());
		restartButton.setOnAction(action -> handleRestartButtonAction());

		if (baseService.getUnoService().getState().getWinner().equals(baseService.getUnoService().getPlayerName())) {
			message.setText("Congrats, you have won the game!");
			imgView.setImage(new Image(EndScreenController.class.getResourceAsStream("/images/success.png")));
		} else {
			message.setText("Fail, you have lost the game!");
			imgView.setImage(new Image(EndScreenController.class.getResourceAsStream("/images/fail.png")));
		}

		if (baseService.getUnoService() != null) {
			baseService.getUnoService().addRequestEventListener(this);
		}
	}


	private void handleExitButtonAction() {
		logger.info("Exit button pressed");
		baseService.getUnoService().stopService();
		System.exit(0);
	}

	private void handleRestartButtonAction() {
		logger.info("Restart button pressed");
		baseService.getUnoService().getClient().sendRequest(Request.Command.RESTART, baseService.getUnoService().getPlayerName());
	}

	public synchronized void playerJoined(State state) {
		// default empty
	}

	public synchronized void gameStarted(State state) {
		// default empty
	}

	public synchronized void played(State state) {
		// default empty
	}

	public synchronized void finished(State state) {
		// default empty
	}

	public synchronized void restarted(State state) {
		baseService.getNavigationService().handleNavigation("main");
	}
}
