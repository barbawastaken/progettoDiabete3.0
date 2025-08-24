package controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class MainController {
    @FXML
    private BorderPane mainContainer;

    @FXML
    private HBox navBarContainer;

    @FXML
    private void onLogoutPressed(){}


    @FXML
    private void onProfiloClicked(){}

    private NavBar navBar;

    @FXML
    public void initialize() {
        // Set up the navigation bar
        navBar = new NavBar();
        navBarContainer.getChildren().add(navBar);

        // Register this controller with the controller.ViewNavigator
        ViewNavigator.setMainController(this);

        // Load the home view by default
        ViewNavigator.navigateToSterco();
    }

    /**
     * Set the content of the main area
     */
    public void setContent(Node content) {
        mainContainer.setCenter(content);
    }

    /**
     * Update the navigation bar based on authentication status
     */
    public void updateNavBar(boolean isAuthenticated) {
        navBar.updateAuthStatus(isAuthenticated, ViewNavigator.getAuthenticatedUser());
    }
}