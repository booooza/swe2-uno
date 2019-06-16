package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.presentation.gui.controller.MainController;
import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class MainAppTest extends ApplicationTest {

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;


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

		double height = 625.0;
		double width = 1000.0;

		Scene scene = new Scene(decorator, width, height);
		final ObservableList<String> stylesheets = scene.getStylesheets();
		stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
				JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
				MainApp.class.getResource("/css/uno-dark.css").toExternalForm());

		stage.setTitle("UNO Game");
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/logo.png")));
		stage.setScene(scene);
		stage.show();
		//
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() throws Exception {
		FxToolkit.hideStage();
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}

	@Test
	public void testPlayerNameInput() {

		try {
			clickOn("#playerName");
		} catch(NoSuchMethodError nsmEx){

		}
		write("Player Test");
	}
}