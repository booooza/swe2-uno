package ch.swe2.uno.presentation.gui.controller;

import ch.swe2.uno.presentation.gui.MainApp;
import ch.swe2.uno.presentation.gui.model.NumberCardViewModel;
import ch.swe2.uno.presentation.gui.model.StateViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GameOverviewController {
    @FXML
    private TableView<NumberCardViewModel> player1Table;
    @FXML
    private TableColumn<NumberCardViewModel, String> player1CardColorColumn;
    @FXML
    private TableColumn<NumberCardViewModel, Number> player1CardNumberColumn;
    @FXML
    private TableView<NumberCardViewModel> player2Table;
    @FXML
    private TableColumn<NumberCardViewModel, String> player2CardColorColumn;
    @FXML
    private TableColumn<NumberCardViewModel, Number> player2CardNumberColumn;

    private MainApp mainApp; // Reference to the main application.

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GameOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        player1CardColorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        player1CardNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        player2CardColorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        player2CardNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        player1Table.setItems(mainApp.getState().getPlayers().get(0).getHand());
        player2Table.setItems(mainApp.getState().getPlayers().get(1).getHand());
        mainApp.updateState();
    }
}