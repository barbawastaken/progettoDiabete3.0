package controller.Diabetologo;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class ModificaDiabetologoController {

    private String taxCode;
    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    @FXML private HBox topBar;

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField password;
    @FXML private TextField taxCodeFXML;
    @FXML private TextField email;
    @FXML private TextField telefono;
    @FXML private TextField dataNascita;
    @FXML private TextField sesso;
    @FXML private TextField indirizzo;
    @FXML private TextField numero;
    @FXML private TextField citta;
    @FXML private TextField cap;


    public void setTaxCode(String taxCode) { this.taxCode = taxCode; inizialize();}

    private void inizialize() {

        String query = "SELECT nome, cognome, password, taxCode, email, telephoneNumber, birthday, gender, " +
                "address, number, city, cap FROM utenti WHERE taxCode = ?";


        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);

            ResultSet rs = stmt.executeQuery();

            this.nome.setText(rs.getString("nome"));
            this.nome.setEditable(false);

            this.cognome.setText(rs.getString("cognome"));
            this.cognome.setEditable(false);

            this.password.setText(rs.getString("password"));
            this.password.setEditable(false);

            this.taxCodeFXML.setText(rs.getString("taxCode"));
            this.taxCodeFXML.setEditable(false);

            this.email.setText(rs.getString("email"));

            this.telefono.setText(rs.getString("telephoneNumber"));

            this.dataNascita.setText(rs.getString("birthday"));
            this.dataNascita.setEditable(false);

            this.sesso.setText(rs.getString("gender"));
            this.sesso.setEditable(false);

            TextFormatter<String> altezzaFormatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*\\.?\\d*")) {
                    return change; // accetta il cambiamento
                } else {
                    return null; // rifiuta il cambiamento
                }
            });

            TextFormatter<String> pesoFormatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*\\.?\\d*")) {
                    return change; // accetta il cambiamento
                } else {
                    return null; // rifiuta il cambiamento
                }
            });

            this.indirizzo.setText(rs.getString("address"));
            this.numero.setText(rs.getString("number"));
            this.citta.setText(rs.getString("city"));
            this.cap.setText(rs.getString("cap"));


        } catch (Exception e) {
            System.out.println("Errore caricamento dati utente: " + e);
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

            DiabetologoController diabetologoController = new DiabetologoController();
            diabetologoController.setTaxCode(taxCode);
            loader.setController(diabetologoController);

            Parent root = loader.load();
            //DiabetologoController diabetologoController = loader.getController();
            //diabetologoController.setTaxCode(taxCode);
            Stage stage = new Stage();
            stage.setTitle("Diabetolgo");
            stage.setScene(new Scene(root, 650, 500));
            stage.show();

        } catch (IOException e) { System.out.println("Errore caricamento homepage diabetologo!" + e.getMessage()); }

        Stage profiloPaziente = (Stage)topBar.getScene().getWindow();
        profiloPaziente.close();

    }

    @FXML void onSendButtonPressed(){

        if(!checkDati()) return;

        String query = "UPDATE utenti SET email = ?, telephoneNumber = ?, address = ?, number = ?, city = ?, cap = ? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email.getText());
            stmt.setString(2, telefono.getText());
            stmt.setString(3, indirizzo.getText());
            stmt.setString(4, numero.getText());
            stmt.setString(5, citta.getText());
            stmt.setString(6, cap.getText());
            stmt.setString(7, taxCode);
            stmt.executeUpdate();
            System.out.println("Salvataggio modifiche eseguito!");
            onHomePagePressed();

        } catch (Exception e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }

    }

    private String arrotonda(String valore) {
        return new BigDecimal(valore)
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();
    }

    public boolean checkDati(){

        if(email.getText().isEmpty() || telefono.getText().isEmpty() ||
                indirizzo.getText().isEmpty() || numero.getText().isEmpty() || citta.getText().isEmpty()|| cap.getText().isEmpty()) {
            messaggioErrore("Uno o più campi sono vuoti!");
            return false;
        }

        Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        if(!validEmail.matcher(email.getText()).matches()) { messaggioErrore("L'indirizzo email specificato non è valido!"); return false;}

        Pattern validTelephone = Pattern.compile("^\\d{10}$");
        if(!validTelephone.matcher(telefono.getText()).matches()) { messaggioErrore("Il numero di telefono specificato non è valido!"); return false;}

        Pattern validCap = Pattern.compile("^\\d{5}$");
        if(!validCap.matcher(cap.getText()).matches()) { messaggioErrore("Il cap specificato non è valido!");return false;}

        Pattern validNumber = Pattern.compile("^\\d{1,3}$");
        if(!validNumber.matcher(numero.getText()).matches()) { messaggioErrore("Il numero civico specificato non è valido!"); return false;}


        return true;
    }

    public void messaggioErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}



