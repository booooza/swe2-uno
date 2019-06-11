package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.services.BaseService;
import ch.swe2.uno.presentation.services.UnoService;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@ViewController(value = "/fxml/views/EndScreen.ch.swe2.uno.presentation.fxml", title = "Game Overview")
public class EndScreenController {
	private static Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);


    @FXML
	private ImageView imgView;
	
	@FXML
	private Label message;

	@Inject
	private BaseService baseService;

	@PostConstruct
	public void init() {
		// Show Image a or image B

		if (baseService.getUnoService().getState().getWinner().equals(baseService.getUnoService().getPlayerName())) {
			message.setText("Congrats, you have won the game!");
			imgView.setImage(new Image(EndScreenController.class.getResourceAsStream("/images/success.png")));
		} else {
			message.setText("Fail, you have lost the game!");
			imgView.setImage(new Image(EndScreenController.class.getResourceAsStream("/images/fail.png")));
		}
	}

	
	public void handleExitButtonAction() {
		logger.info("Exit button pressed");
		System.exit(0);
	}
}
