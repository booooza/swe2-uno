package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.presentation.gui.controller.MainController;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);

	private static Stage primaryStage;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;

	public static void main(String[] args) {
		logger.info("Main started");
		launch(MainApp.class, args);
		logger.info("Main ended");
	}

	@Override
	public void start(Stage stage) throws Exception {
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

		setPrimaryStage(stage);

		stage.setTitle("UNO Game");
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/logo.png")));
		stage.setScene(scene);
		stage.show();
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	private void setPrimaryStage(Stage pStage) {
		MainApp.primaryStage = pStage;
	}
}