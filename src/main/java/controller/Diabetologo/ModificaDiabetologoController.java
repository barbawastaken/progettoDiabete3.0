package controller.Diabetologo;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class ModificaDiabetologoController {

    private final Session userData = Session.getInstance();
    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    @FXML private HBox navbarContainer;
    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField taxCodeFXML;
    @FXML private TextField email;
    @FXML private TextField telefono;
    @FXML private TextField dataNascita;
    @FXML private TextField sesso;
    @FXML private TextField indirizzo;
    @FXML private TextField numero;
    @FXML private TextField citta;
    @FXML private TextField cap;

    @FXML private Text nuovaPasswordText;
    @FXML private TextField nuovaPasswordField;
    @FXML private Text confermaPasswordText;
    @FXML private TextField confermaPasswordField;
    @FXML private Button confermaPasswordButton;

    @FXML
    private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        String query = "SELECT nome, cognome, password, taxCode, email, telephoneNumber, birthday, gender, address, number, city, cap FROM utenti WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userData.getTaxCode());

            ResultSet rs = stmt.executeQuery();

            this.nome.setText(rs.getString("nome"));
            this.nome.setEditable(false);

            this.cognome.setText(rs.getString("cognome"));
            this.cognome.setEditable(false);

            this.taxCodeFXML.setText(rs.getString("taxCode"));
            this.taxCodeFXML.setEditable(false);

            this.email.setText(rs.getString("email"));

            this.telefono.setText(rs.getString("telephoneNumber"));

            this.dataNascita.setText(rs.getString("birthday"));
            this.dataNascita.setEditable(false);

            this.sesso.setText(rs.getString("gender"));
            this.sesso.setEditable(false);

            this.indirizzo.setText(rs.getString("address"));
            this.numero.setText(rs.getString("number"));
            this.citta.setText(rs.getString("city"));
            this.cap.setText(rs.getString("cap"));


        } catch (Exception e) {
            System.out.println("Errore caricamento dati utente: " + e);
        }

        cap.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });

        telefono.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });

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
            stmt.setString(7, userData.getTaxCode());
            stmt.executeUpdate();

            ViewNavigator.navigateToProfileDiabetologo();

        } catch (Exception e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }

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

        if (!numero.getText().matches("\\d+([/]?[A-Za-z])?")) { messaggioErrore("Numero civico non è valido!"); return false; }

        return true;
    }

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
    public void onCambiaPasswordPressed(){

        if(!nuovaPasswordField.getText().equals(confermaPasswordField.getText())){
            messaggioErrore("La conferma della password non è corretta");
            confermaPasswordField.setText("");
            return;
        }

        if(nuovaPasswordField.getText().equals(userData.getPassword())){
            messaggioErrore("La password deve essere diversa dalla vecchia");
            nuovaPasswordField.setText("");
            confermaPasswordField.setText("");
            return;
        }

        String nuovaPassword = nuovaPasswordField.getText();

        String query = "UPDATE utenti SET password=? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nuovaPassword);
            stmt.setString(2, userData.getTaxCode());

            stmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("Errore nel salvataggio della password: " + e.getMessage());
            messaggioErrore("Password non salvata correttamente!");
        }

        query = "UPDATE loginTable SET password=? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, nuovaPassword);
            stmt.setString(2, userData.getTaxCode());

            stmt.executeUpdate();

            ViewNavigator.navigateToProfileDiabetologo();

        } catch (Exception e) {
            System.out.println("Errore nel salvataggio della password (loginTable): " + e.getMessage());
            messaggioErrore("Password non salvata correttamente!");
        }

    }
}



