package ch.swe2.uno.presentation.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class MainAppTest extends ApplicationTest {

	@Override
	public void start(Stage stage) throws Exception {
		Parent mainNode = FXMLLoader.load(MainApp.class.getClassLoader().getResource("fxml/main.fxml"));
		stage.setScene(new Scene(mainNode));
		stage.show();
		stage.toFront();
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
		clickOn("#playerName");
		write("Player Test");
	}
}