package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.presentation.gui.controller.ColorDialogController;
import ch.swe2.uno.presentation.gui.controller.EndScreenController;
import ch.swe2.uno.presentation.gui.controller.GameOverviewController;
import ch.swe2.uno.presentation.gui.controller.MainController;
import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainApp extends Application {
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);

	private Stage primaryStage;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;

	public static void main(String[] args) {
		launch(MainApp.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		new Thread(() -> {
			try {
				SVGGlyphLoader.loadGlyphsFont(MainApp.class.getResourceAsStream("/fonts/icomoon.svg"),
						"icomoon.svg");
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			}
		}).start();

		Flow flow = new Flow(MainController.class);
		DefaultFlowContainer container = new DefaultFlowContainer();
		flowContext = new ViewFlowContext();
		flowContext.register("Stage", stage);
		flow.createHandler(flowContext).start(container);

		JFXDecorator decorator = new JFXDecorator(stage, container.getView());
		decorator.setCustomMaximize(true);
		decorator.setGraphic(new SVGGlyph(""));

		double width = 800;
		double height = 600;
		try {
			Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
			width = bounds.getWidth() / 2.5;
			height = bounds.getHeight() / 1.35;
		} catch (Exception e) {
		}

		Scene scene = new Scene(decorator, width, height);
		final ObservableList<String> stylesheets = scene.getStylesheets();
		stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
				JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
				MainApp.class.getResource("/css/uno-main.css").toExternalForm());

		this.primaryStage = stage;

		stage.setTitle("UNO Game");
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/logo.png")));
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Shows the player overview inside the root layout.
	 */
	public void showGameOverview() {
		try {
			// Load game overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/views/GameOverview.fxml"));
			AnchorPane gameOverview = loader.load();

			// Set game overview into the center of root layout.
			//rootLayout.setCenter(gameOverview);

			// Give the controller access to the main app.
			GameOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			logger.warn(String.format("Exception: %s", e.getMessage()));
		}
	}

	/**
	 * Opens a dialog to choose color for the specified card.
	 */
	public UnoColor showColorDialog() {
		try {
			// Load the ch.swe2.uno.presentation.fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/views/ColorDialog.fxml"));
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Choose color");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
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

	public void showEndScreen() {
		try {
			// Load end screen.
			FXMLLoader loader = new FXMLLoader();

			AnchorPane endScreen = loader.load();

			// Set welcome screen into the center of root layout.
			//rootLayout.setCenter(endScreen);

			// Give the controller access to the main app.
			EndScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			logger.warn(String.format("Exception: %s", e.getMessage()));
		}
	}
}