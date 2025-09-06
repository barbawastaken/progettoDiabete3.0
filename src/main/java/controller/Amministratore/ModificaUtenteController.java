package controller.Amministratore;

import controller.LoginController;
import controller.NavBar;
import controller.NavBarTags;
import controller.Paziente.PazienteController;
import controller.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amministratore.ModificaUtenteModel;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class ModificaUtenteController extends GestioneUtenti{
    private Utente utente;
    private  VisualizzaListaUtentiModel model;
    private  ModificaUtenteModel modificaUtenteModel;
    private Stage listaUtentiStage;
    private VisualizzaListaUtentiController listaUtentiController;
    //QUESTI FXML SONO SOLO PER MODIFICA NON ANCHE PER AGGIUNGI
    @FXML private HBox navbarContainer;
    @FXML private Text nuovaPasswordText;
    @FXML private TextField nuovaPasswordField;
    @FXML private Text confermaPasswordText;
    @FXML private TextField confermaPasswordField;
    @FXML private Button confermaPasswordButton;


    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    
    
    @FXML
    public void initialize() {


        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE_toHomepage);
        navbarContainer.getChildren().add(navbar);

        this.listaUtentiController = new VisualizzaListaUtentiController();
        this.utente = Session.getInstance().getUtenteInEsame();
        this.model = new VisualizzaListaUtentiModel();
        this.modificaUtenteModel = new ModificaUtenteModel();


        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        email.setText(utente.getEmail());
        password.setText(utente.getPassword());
        address.setText(utente.getAddress());
        gender.setValue(utente.getGender());
        telephone.setText(utente.getTelephone());
        taxCode.setText(utente.getTaxCode());
        cap.setText(String.valueOf(utente.getCap()));
        number.setText(String.valueOf(utente.getNumber()));
        citta.setText(utente.getCity());
        nation.setText(utente.getCountryOfResidence());
        birthday.setValue(LocalDate.now());

        if(utente.getRole().equals("PAZIENTE")){
            medicoCurante.setVisible(true);
            medicoCurante.setManaged(true);
            medicoCuranteText.setVisible(true);
            medicoCuranteText.setManaged(true);
            medicoCurante.setValue(utente.getDiabetologo());
            height.setVisible(true);
            height.setManaged(true);
            heightText.setVisible(true);
            heightText.setManaged(true);
            height.setText(String.valueOf(utente.getHeight()));
            weight.setVisible(true);
            weight.setManaged(true);
            weightText.setVisible(true);
            weightText.setManaged(true);
            weight.setText(String.valueOf(utente.getWeight()));

            try {
                HashMap<String, String> map = modificaUtenteModel.getDiabetologi();
                for(String s : map.keySet()){
                    medicoCurante.getItems().add(s);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void onIndietroButtonPressed(){ ((Stage) nome.getScene().getWindow()).close(); }

    @FXML
    private void onSendButtonPressed() throws SQLException {
        String ruoloSelezionato = utente.getRole();
        boolean isPaziente;
        if(ruoloSelezionato.equals("PAZIENTE")) {
            isPaziente = true;
            super.checkForPazienti();
        } else{
            isPaziente = false;
            super.check();
        }


        double peso = 0.0;
        double altezza = 0.0;
        if (ruoloSelezionato.equals("PAZIENTE")) {
            try {
                System.out.println("paziente selezionato");
                peso = Double.parseDouble(weight.getText());
                altezza = Double.parseDouble(height.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Peso e altezza devono essere validi.");
                alert.showAndWait();
                return;
            }
        }
        if(isPaziente && !checkForPazienti()){
            Utente aggiornato = new Utente(
                    taxCode.getText(),
                    password.getText(),
                    nome.getText(),
                    cognome.getText(),
                    email.getText(),
                    java.sql.Date.valueOf(birthday.getValue()),
                    address.getText(),
                    Integer.parseInt(number.getText()),
                    citta.getText(),
                    Integer.parseInt(cap.getText()),
                    nation.getText(),
                    gender.getValue(),
                    telephone.getText(),
                    ruoloSelezionato,
                    medicoCurante.getValue(),
                    peso,
                    altezza
            );
            System.out.println("birthday: " + aggiornato.getBirthday());
            modificaUtenteModel.aggiornaUtente(utente.getTaxCode(), aggiornato);
            if(listaUtentiController != null){ listaUtentiController.aggiornaTabellaUtenti();}
            Stage currentStage = (Stage) nome.getScene().getWindow();
            currentStage.close();
        } else if (!isPaziente && !check()) {
            Utente aggiornato = new Utente(
                    taxCode.getText(),
                    password.getText(),
                    nome.getText(),
                    cognome.getText(),
                    email.getText(),
                    java.sql.Date.valueOf(birthday.getValue()),
                    address.getText(),
                    Integer.parseInt(number.getText()),
                    citta.getText(),
                    Integer.parseInt(cap.getText()),
                    nation.getText(),
                    gender.getValue(),
                    telephone.getText(),
                    ruoloSelezionato,
                    null,
                    0,
                    0
            );

            System.out.println("Valore nazione: " + aggiornato.getNation());
            System.out.println("birthday: " + aggiornato.getBirthday());
            modificaUtenteModel.aggiornaUtente(utente.getTaxCode(), aggiornato);
            if(listaUtentiController != null){ listaUtentiController.aggiornaTabellaUtenti();}
            Stage currentStage = (Stage) nome.getScene().getWindow();
            currentStage.close();

        }
    }
    public void setUtente(Utente utente){ this.utente = utente; }

    // PARTE AGGIUNTA DA ME (ANDREA) 11 LUGLIO PER BARRA BLU CON LOGOUT E HOME + PASSWORD + NUOVA PASSWORD
    public void messaggioErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    @FXML
    public void onPasswordPressed(){
        nuovaPasswordText.setVisible(true);
        nuovaPasswordField.setVisible(true);
        confermaPasswordText.setVisible(true);
        confermaPasswordField.setVisible(true);
        confermaPasswordButton.setVisible(true);
    }

    @FXML
    public void onCambiaPasswordPressed() {
        String nuovaPassword = nuovaPasswordField.getText();
        String confermaPassword = confermaPasswordField.getText();

        if (!nuovaPassword.equals(confermaPassword)) {
            messaggioErrore("La conferma della password non è corretta");
            return;
        }

        String passwordCriptata = BCrypt.hashpw(nuovaPassword, BCrypt.gensalt());

        String query1 = "UPDATE utenti SET password = ? WHERE taxCode = ?";
        String query2 = "UPDATE loginTable SET password = ? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt1 = conn.prepareStatement(query1);
             PreparedStatement stmt2 = conn.prepareStatement(query2)) {

            stmt1.setString(1, passwordCriptata);
            stmt1.setString(2, taxCode.getText());
            stmt1.executeUpdate();

            stmt2.setString(1, passwordCriptata);
            stmt2.setString(2, taxCode.getText());
            stmt2.executeUpdate();

            // Aggiorna il campo password nel form
            password.setText(nuovaPassword);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText(null);
            alert.setContentText("Password aggiornata con successo!");
            alert.showAndWait();

            nuovaPasswordText.setVisible(false);
            nuovaPasswordField.setVisible(false);
            confermaPasswordText.setVisible(false);
            confermaPasswordField.setVisible(false);
            confermaPasswordButton.setVisible(false);
        } catch (Exception e) {
            System.out.println("Errore nel salvataggio della password: " + e.getMessage());
            messaggioErrore("Password non salvata correttamente!");
        }
    }
    @FXML
    private void onLogoutPressed(){

        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setTaxCode(taxCode.getText());
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }



    }
    public void setTaxCode(String taxCode) {
        this.taxCode.setText(taxCode);
    }

    @FXML void onHomePagePressed(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/amministratore_view.fxml.fxml"));

            Parent root = loader.load();
            ModificaUtenteController modificaUtenteController = loader.getController();
            modificaUtenteController.setTaxCode(taxCode.getText());
            Stage stage = new Stage();
            stage.setTitle("Amministratore");
            stage.setScene(new Scene(root, 650, 500));
            stage.show();

        } catch (IOException e) { System.out.println("Errore caricamento homepage utente!" + e.getMessage()); }


    }
}

