package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.datafx.ExtendedAnimatedFlowContainer;
import ch.swe2.uno.presentation.services.BaseService;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

@ViewController(value = "/fxml/Main.fxml", title = "Main")
public final class MainController {
	private static Logger logger = LoggerFactory.getLogger(MainController.class);
	private static ViewFlowContext staContext;
	@FXMLViewFlowContext
	private ViewFlowContext context;
	@FXML
	private StackPane root;
	@ActionHandler
	private FlowActionHandler actionHandler;
	@Inject
	private BaseService baseService;

	public static ViewFlowContext getMainControllerViewFlowContext() {
		return staContext;
	}

	@PostConstruct
	public void init() {
		context = new ViewFlowContext();
		Flow innerFlow = new Flow(WelcomeScreenController.class);
		final FlowHandler flowHandler = innerFlow.createHandler(context);
		context.register("ContentFlowHandler", flowHandler);
		context.register("ContentFlow", innerFlow);
		MainController.staContext = context;
		try {
			final Duration containerAnimationDuration = Duration.millis(320);
			root.getChildren()
					.add(flowHandler.start(new ExtendedAnimatedFlowContainer(containerAnimationDuration, SWIPE_LEFT)));
			context.register("ContentPane", root.getChildren().get(0));
		} catch (FlowException flowEx) {
			logger.error(String.format("Exception: %s", flowEx.getMessage()), flowEx);
		}
		baseService.initNavigationService();
	}
}
