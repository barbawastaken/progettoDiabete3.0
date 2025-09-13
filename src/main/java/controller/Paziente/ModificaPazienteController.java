package controller.Paziente;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;

public class ModificaPazienteController {

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
    @FXML private TextField altezza;
    @FXML private TextField peso;
    @FXML private TextField indirizzo;
    @FXML private TextField numero;
    @FXML private TextField citta;
    @FXML private TextField cap;

    @FXML private Text erroreEmail;
    @FXML private Text erroreTelefono;
    @FXML private Text erroreAltezza;
    @FXML private Text errorePeso;
    @FXML private Text erroreIndirizzo;
    @FXML private Text erroreNumero;
    @FXML private Text erroreCitta;
    @FXML private Text erroreCap;

    @FXML private Text nuovaPasswordText;
    @FXML private TextField nuovaPasswordField;
    @FXML private Text confermaPasswordText;
    @FXML private TextField confermaPasswordField;
    @FXML private Button confermaPasswordButton;

    @FXML
    public void initialize() {

        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        Session.getInfos();

        this.nome.setText(userData.getNome());
        this.cognome.setText(userData.getCognome());
        this.taxCodeFXML.setText(userData.getTaxCode());
        this.email.setText(userData.getEmail());
        this.telefono.setText(userData.getTelephone());
        this.dataNascita.setText(userData.getBirthday());
        this.altezza.setText(userData.getHeight().toString());
        this.peso.setText(userData.getWeight().toString());
        this.sesso.setText(userData.getSex());
        this.citta.setText(userData.getCity());
        this.indirizzo.setText(userData.getAddress());
        this.cap.setText(userData.getCap().toString());
        this.numero.setText(userData.getCivic());

        cap.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });

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

        altezza.setTextFormatter(altezzaFormatter);
        peso.setTextFormatter(pesoFormatter);
    }

    @FXML void onSendButtonPressed(){

        if(!checkDati()) return;

        String query = "UPDATE utenti SET email = ?, telephoneNumber = ?, Altezza = ?, Peso = ?, address = ?, number = ?, city = ?, cap = ? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email.getText());
            stmt.setString(2, telefono.getText());
            stmt.setString(3, arrotonda(altezza.getText()));
            stmt.setString(4, arrotonda(peso.getText()));
            stmt.setString(5, indirizzo.getText());
            stmt.setString(6, numero.getText());
            stmt.setString(7, citta.getText());
            stmt.setString(8, cap.getText());

            stmt.setString(9, userData.getTaxCode());

            stmt.executeUpdate();

            ViewNavigator.navigateToProfilePaziente();

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

        if(email.getText().isEmpty() || telefono.getText().isEmpty() || altezza.getText().isEmpty() || peso.getText().isEmpty() ||
        indirizzo.getText().isEmpty() || numero.getText().isEmpty() || citta.getText().isEmpty()|| cap.getText().isEmpty()) {
            messaggioErrore("Uno o più campi sono vuoti!");
        }

        if(email.getText().isEmpty()) { erroreEmail.setVisible(true); return false; }

        if(telefono.getText().isEmpty()) { erroreTelefono.setVisible(true); return false; }

        if(altezza.getText().isEmpty()) { erroreAltezza.setVisible(true); return false; }

        if(peso.getText().isEmpty()) { errorePeso.setVisible(true); return false; }

        if(indirizzo.getText().isEmpty()) { erroreIndirizzo.setVisible(true); return false; }

        if(numero.getText().isEmpty()) { erroreNumero.setVisible(true); return false; }

        if(citta.getText().isEmpty()) { erroreCitta.setVisible(true); return false; }

        if(cap.getText().isEmpty()) { erroreCap.setVisible(true); return false; }

        Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        if(!validEmail.matcher(email.getText()).matches()) { erroreEmail.setVisible(true); return false;}

        Pattern validTelephone = Pattern.compile("^\\d{10}$");
        if(!validTelephone.matcher(telefono.getText()).matches()) { erroreTelefono.setVisible(true); return false;}

        Pattern validCap = Pattern.compile("^\\d{5}$");
        int capInt = Integer.parseInt(cap.getText());
        if(!validCap.matcher(cap.getText()).matches() && capInt > 0) { erroreCap.setVisible(true); return false;}

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

        String nuovaPassword = confermaPasswordField.getText();

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

            ViewNavigator.navigateToProfilePaziente();

        } catch (Exception e) {
            System.out.println("Errore nel salvataggio della password (loginTable): " + e.getMessage());
            messaggioErrore("Password non salvata correttamente!");
        }

    }
}



