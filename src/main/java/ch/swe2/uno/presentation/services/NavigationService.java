package ch.swe2.uno.presentation.services;

import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.controller.*;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
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

	public NavigationService() {
		logger.info("Navigation Service constructed");
	}

	public static NavigationService getInstance() {
		if (theInstance == null) {
			theInstance = new NavigationService();
		}
		return theInstance;
	}

	public void initNavigationService() {
		logger.info(String.format("initNavigationService {}", MainController.getMainControllerViewFlowContext()));

		FlowHandler flowHandler = (FlowHandler) MainController.getMainControllerViewFlowContext()
				.getRegisteredObject("ContentFlowHandler");
		Flow contentFlow = (Flow) MainController.getMainControllerViewFlowContext().getRegisteredObject("ContentFlow");
		this.bindNodeToController("Main", MainController.class, contentFlow, flowHandler);
		this.bindNodeToController("WelcomeScreen", WelcomeScreenController.class, contentFlow, flowHandler);
		this.bindNodeToController("GameOverview", GameOverviewController.class, contentFlow, flowHandler);
		this.bindNodeToController("EndScreen", EndScreenController.class, contentFlow, flowHandler);

		logger.info(String.format("Nodes bound {}", MainController.getMainControllerViewFlowContext()));
	}

	public void handleNavigation(String navTarget) {
		try {
			logger.info(String.format("Navigation Service handleNavigation to %s", navTarget));
			if (MainController.getMainControllerViewFlowContext() != null) {
				FlowHandler flowHandler = (FlowHandler) MainController.getMainControllerViewFlowContext()
						.getRegisteredObject("ContentFlowHandler");
				flowHandler.handle(navTarget);
			} else {
				logger.info(
						String.format("Navigation Service handleNavigation context is null navTarget: %s", navTarget));
			}
		} catch (VetoException ex) {
			logger.error(String.format("Error navigation to %s", navTarget), ex);
		} catch (FlowException ex) {
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
			e.printStackTrace();
			return UnoColor.BLACK;
		}
	}

	private void bindNodeToController(String node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
		flow.withGlobalLink(node, controllerClass);
	}
}