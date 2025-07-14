package controller.Amministratore;

import controller.Diabetologo.DiabetologoController;
import controller.LoginController;
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
    @FXML private HBox topBar;
    private String taxCode;

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }
    @FXML
    private void isInserisciUtenteClicked(ActionEvent event) throws IOException {
        AggiungiUtenteController controller = new AggiungiUtenteController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/aggiungi_utente_view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Aggiungi Utente");
        stage.setScene(new Scene(root));
        stage.show();

        //Chiudete le finestre
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void isVisualizzaUtentiClicked(ActionEvent event) throws IOException {
        VisualizzaListaUtentiController controller = new VisualizzaListaUtentiController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/visualizza_utenti_view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Visualizza Utente");
        stage.setScene(new Scene(root));
        stage.show();

        //Chiudete le finestre
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
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

        Stage pazienteStage = (Stage)topBar.getScene().getWindow();
        pazienteStage.close();

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

        Stage amministratore = (Stage)topBar.getScene().getWindow();
        amministratore.close();

    }

    }

