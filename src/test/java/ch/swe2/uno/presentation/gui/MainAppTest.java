package ch.swe2.uno.presentation.gui;

import ch.swe2.uno.presentation.gui.controller.MainController;
import com.jfoenix.assets.JFoenixResources;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class MainAppTest {
	private static Logger logger = LoggerFactory.getLogger(MainAppTest.class);

	private static Stage primaryStage;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;

	@Start
	private void start(Stage stage) throws Exception {
		Flow flow = new Flow(MainController.class);
		DefaultFlowContainer container = new DefaultFlowContainer();
		flowContext = new ViewFlowContext();
		flowContext.register("Stage", stage);
		flow.createHandler(flowContext).start(container);

		double height = 625.0;
		double width = 1000.0;

		Scene scene = new Scene(container.getView(), width, height);
		final ObservableList<String> stylesheets = scene.getStylesheets();
		stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
				JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
				MainApp.class.getResource("/css/uno-dark.css").toExternalForm());

		MainApp.setPrimaryStage(stage);

		stage.setTitle("UNO Game");
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/logo.png")));
		stage.setScene(scene);
		stage.show();
	}

	@Test
	@Order(1)
	public void server_button_should_contain_text(FxRobot robot) {
		Assertions.assertThat(robot.lookup("#serverButton").queryAs(Button.class)).hasText("Start Server");
	}

	@Test
	@Order(2)
	public void player_name_should_change(FxRobot robot) {
		robot.clickOn("#playerName");
		robot.write("Test Name");
		Assertions.assertThat(robot.lookup("#playerName").queryAs(TextField.class)).hasText("Test Name");
	}

	@Test
	@Order(3)
	public void cannot_join_without_server_started(FxRobot robot) {
		robot.clickOn("#playerName");
		robot.write("Test Name");
		robot.clickOn("#joinButton");
		Assertions.assertThat(robot.lookup("#joinButton").queryAs(Button.class)).hasText("Join");
	}

	@Test
	@Order(4)
	public void server_button_click_should_start_server(FxRobot robot) {
		robot.clickOn("#serverButton");
		Assertions.assertThat(robot.lookup("#serverButton").queryAs(Button.class)).hasText("Server Started...");
	}

	@Test
	@Order(5)
	public void join_button_changes_text_after_join(FxRobot robot) {
		robot.clickOn("#playerName");
		robot.write("Test Name");
		robot.clickOn("#joinButton");
		Assertions.assertThat(robot.lookup("#joinButton").queryAs(Button.class)).hasText("Joined");
	}
}