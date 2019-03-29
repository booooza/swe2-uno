package ch.swe2.uno.presentation.gui.controller;


import ch.swe2.uno.business.card.ICard;
import ch.swe2.uno.presentation.gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GameOverviewController {
    @FXML
    private TableView<ICard> player1Table;
    @FXML
    private TableColumn<ICard, String> player1CardColorColumn;
    @FXML
    private TableColumn<ICard, Number> player1CardNumberColumn;
    @FXML
    private TableView<ICard> player2Table;
    @FXML
    private TableColumn<ICard, String> player2CardColorColumn;
    @FXML
    private TableColumn<ICard, Number> player2CardNumberColumn;

    // Reference to the main application.
    private MainApp mainApp;

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
        player1Table.setItems(mainApp.getPlayer1Data());
        player2Table.setItems(mainApp.getPlayer2Data());
    }
}