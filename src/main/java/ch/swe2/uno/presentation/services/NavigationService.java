package ch.swe2.uno.presentation.services;

import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.controller.*;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NavigationService {
	private static Logger logger = LoggerFactory.getLogger(NavigationService.class);
	private static NavigationService theInstance;

	private ViewFlowContext context;

	private NavigationService() {
		logger.info("NavigationService created");
	}

	static NavigationService getInstance() {
		if (theInstance == null) {
			theInstance = new NavigationService();
		}
		return theInstance;
	}

	void initNavigationService(ViewFlowContext context) {
		this.context = context;
		Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
		this.bindNodeToController("Main", MainController.class, contentFlow);
		this.bindNodeToController("WelcomeScreen", WelcomeScreenController.class, contentFlow);
		this.bindNodeToController("GameOverview", GameOverviewController.class, contentFlow);
		this.bindNodeToController("EndScreen", EndScreenController.class, contentFlow);
	}

	public void handleNavigation(String navTarget) {
		try {
			logger.info(String.format("Navigation Service handleNavigation to %s", navTarget));
			FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
			contentFlowHandler.handle(navTarget);
		} catch (FlowException | VetoException ex) {
			logger.error(String.format("Error navigation to %s", navTarget), ex);
		}
	}

	/**
	 * Opens a dialog to choose color for the specified card.
	 */
	public UnoColor showColorDialog() {
		try {
			// Load the ch.swe2.uno.presentation.fxml file and create a new stage for the
			// popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NavigationService.class.getResource("/fxml/views/ColorDialog.fxml"));
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Choose color");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(MainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Show the dialog and wait until the user closes it
			ColorDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();

			return controller.getChosenColor();
		} catch (IOException e) {
			logger.error("Can not load color dialog, fallback to BLACK {}", e);
			return UnoColor.BLACK;
		}
	}

	private void bindNodeToController(String node, Class<?> controllerClass, Flow flow) {
		flow.withGlobalLink(node, controllerClass);
	}
}