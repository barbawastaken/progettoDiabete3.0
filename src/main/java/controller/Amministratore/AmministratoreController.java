package controller.Amministratore;

import controller.LoginController;
import controller.NavBar;
import controller.NavBarTags;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;



public class AmministratoreController {
    @FXML private HBox navbarContainer;
    private String taxCode;

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }
    @FXML
    private void isInserisciUtenteClicked()  {
        ViewNavigator.navigateToAddUser();
    }

    @FXML
    private void initialize(){
        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);
    }

    @FXML
    private void isVisualizzaUtentiClicked(ActionEvent event) throws IOException {
        ViewNavigator.navigateToVisualizzaUtenti();
    }

    @FXML
    private void isVisualizzaStatisticheClicked() {

        try {

            Stage stage = new Stage();
            stage.setTitle("Statistiche");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/visualizzaStatistiche.fxml"));

            VisualizzaStatisticheController visualizzaStatisticheController = loader.getController();

            Parent root = loader.load();
            stage.setScene(new Scene(root, 1000, 650));
            stage.show();

            // Chiudi la finestra corrente


        } catch (IOException e) { System.out.println("Errore caricamento pagina statistiche!" + e.getMessage()); }

    }

    @FXML
    private void onLogoutPressed(){
        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setTaxCode(taxCode);
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }


    }

    @FXML void onHomePagePressed(ActionEvent event){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/amministratore_view.fxml"));
            Parent root = loader.load();

            AmministratoreController amministratoreController = loader.getController();
            amministratoreController.setTaxCode(taxCode);

            Stage stage = new Stage();
            stage.setTitle("Amministratore");
            stage.setScene(new Scene(root, 650, 500));
            stage.show();

            // Chiudi la finestra corrente
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) { System.out.println("Errore caricamento homepage amministratore!" + e.getMessage()); }



    }

    }

