package controller.Diabetologo;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ModificaDiabetologoController {

    private String taxCode;

    @FXML private HBox topBar;

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField password;
    @FXML private TextField taxCodeFXML;
    @FXML private TextField email;
    @FXML private TextField telefono;
    @FXML private DatePicker dataNascita;
    @FXML private ChoiceBox<String> sesso;
    @FXML private TextField indirizzo;
    @FXML private TextField numero;
    @FXML private TextField citta;
    @FXML private TextField cap;


    public void setTaxCode(String taxCode) { this.taxCode = taxCode; inizialize();}

    private void inizialize() {



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

    @FXML void onHomePagePressed(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/diabetologo_view.fxml"));

            Parent root = loader.load();
            DiabetologoController pazienteController = loader.getController();
            pazienteController.setTaxCode(taxCode);
            Stage stage = new Stage();
            stage.setTitle("Diabetologo");
            stage.setScene(new Scene(root, 650, 500));
            stage.show();

        } catch (IOException e) { System.out.println("Errore caricamento homepage diabetologo!" + e.getMessage()); }

        Stage profiloPaziente = (Stage)topBar.getScene().getWindow();
        profiloPaziente.close();

    }

    @FXML void onSendButtonPressed(){}
}

