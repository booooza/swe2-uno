package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.services.UnoService;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@ViewController(value = "/fxml/views/EndScreen.ch.swe2.uno.presentation.fxml", title = "Game Overview")
public class EndScreenController {
	private static Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);
	private MainApp mainApp; // Reference to the main application.

	@Inject
	private UnoService unoService;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the ch.swe2.uno.presentation.fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	@PostConstruct
	public void init() {
		// Show Image a or image B

		if (unoService.getState().getWinner().equals(unoService.getPlayerName())) {
			//loader.setLocation(MainApp.class.getResource("/fxml/views/SuccessScreen.fxml"));
		} else {
			//loader.setLocation(MainApp.class.getResource("/fxml/views/FailScreen.fxml"));
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void handleExitButtonAction() {
		logger.info("Exit button pressed");
		System.exit(0);
	}
}
