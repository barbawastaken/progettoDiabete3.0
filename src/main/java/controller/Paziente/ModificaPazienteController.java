package controller.Paziente;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Amministratore.AggiungiUtenteModel;
import model.Amministratore.Utente;
import model.Paziente.PazienteModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ModificaPazienteController {

    private final Session userData = Session.getInstance();

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    @FXML private HBox navbarContainer;
    HashMap<String, String> diabetologo = AggiungiUtenteModel.getDiabetologi();

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField taxCode;
    @FXML private TextField email;
    @FXML private TextField telephone;
    @FXML private DatePicker birthday;
    @FXML private ComboBox<String> gender;
    @FXML private TextField height;
    @FXML private TextField weight;
    @FXML private TextField address;
    @FXML private TextField number;
    @FXML private TextField citta;
    @FXML private TextField cap;
    @FXML private TextField nation;


    @FXML private Text emailError;
    @FXML private Text telephoneError;
    @FXML private Text heightError;
    @FXML private Text weightError;
    @FXML private Text addressError;
    @FXML private Text numberError;
    @FXML private Text cityError;
    @FXML private Text capError;
    @FXML private Text nomeError;
    @FXML private Text cognomeError;
    @FXML private Text taxCodeError;
    @FXML private Text birthdayError;
    @FXML private Text genderError;
    @FXML private ComboBox<String> medicoCurante;
    @FXML private Text medicoCuranteText;
    @FXML private Text heightText;
    @FXML private Text weightText;
    @FXML private Text userAddedText;
    @FXML private Text passwordError;
    @FXML private Text nationError;

    @FXML private Text nuovaPasswordText;
    @FXML private TextField nuovaPasswordField;
    @FXML private Text confermaPasswordText;
    @FXML private TextField confermaPasswordField;
    @FXML private Button confermaPasswordButton;

    public ModificaPazienteController() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException {

        this.nation.setText(userData.getCountryOfResidence());

        for(String s : diabetologo.keySet()){
            medicoCurante.getItems().add(s);
        }
        taxCodeError.setVisible(false);
        taxCodeError.setManaged(false);
        numberError.setVisible(false);
        numberError.setManaged(false);
        telephoneError.setVisible(false);
        telephoneError.setManaged(false);
        emailError.setVisible(false);
        emailError.setManaged(false);
        capError.setVisible(false);
        capError.setManaged(false);
        birthdayError.setVisible(false);
        birthdayError.setManaged(false);
        genderError.setVisible(false);
        genderError.setManaged(false);
        weightError.setVisible(false);
        weightError.setManaged(false);
        heightError.setVisible(false);
        heightError.setManaged(false);
        userAddedText.setVisible(false);
        userAddedText.setManaged(false);
        nomeError.setVisible(false);
        nomeError.setManaged(false);
        cognomeError.setVisible(false);
        cognomeError.setManaged(false);
        passwordError.setVisible(false);
        passwordError.setManaged(false);
        nationError.setVisible(false);
        nationError.setManaged(false);
        cityError.setVisible(false);
        cityError.setManaged(false);
        addressError.setVisible(false);
        addressError.setManaged(false);
        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        Session.getInfos();

        gender.getItems().addAll("Maschio", "Femmina");

        this.nome.setText(userData.getNome());
        this.cognome.setText(userData.getCognome());
        this.taxCode.setText(userData.getTaxCode());
        this.email.setText(userData.getEmail());
        this.telephone.setText(userData.getTelephone());
        this.birthday.setValue(Date.valueOf(userData.getBirthday()).toLocalDate());
        this.height.setText(userData.getHeight().toString());
        this.weight.setText(userData.getWeight().toString());
        this.gender.setValue(userData.getSex());
        this.citta.setText(userData.getCity());
        this.address.setText(userData.getAddress());
        this.cap.setText(userData.getCap().toString());
        this.number.setText(userData.getCivic());
        this.medicoCurante.setValue(userData.getMedicoCurante());

        cap.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });

        TextFormatter<String> heightFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change; // accetta il cambiamento
            } else {
                return null; // rifiuta il cambiamento
            }
        });

        TextFormatter<String> weightFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change; // accetta il cambiamento
            } else {
                return null; // rifiuta il cambiamento
            }
        });

        height.setTextFormatter(heightFormatter);
        weight.setTextFormatter(weightFormatter);
    }

    @FXML void onSendButtonPressed() throws SQLException {

        if(!checkDati()) return;

        Utente user = new Utente(
                this.taxCode.getText(),
                null,
                this.nome.getText(),
                this.cognome.getText(),
                this.email.getText(),
                Date.valueOf(this.birthday.getValue()),
                this.address.getText(),
                this.number.getText(),
                this.citta.getText(),
                Integer.parseInt(this.cap.getText()),
                this.nation.getText(),
                this.gender.getValue(),
                this.telephone.getText(),
                "PAZIENTE",
                diabetologo.get(this.medicoCurante.getValue()),
                Double.parseDouble(this.weight.getText()),
                Double.parseDouble(this.height.getText())
        );

        /*
        String query = "UPDATE utenti SET email = ?, telephoneNumber = ?, height = ?, weight = ?, address = ?, number = ?, city = ?, cap = ? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email.getText());
            stmt.setString(2, telephone.getText());
            stmt.setString(3, arrotonda(height.getText()));
            stmt.setString(4, arrotonda(weight.getText()));
            stmt.setString(5, address.getText());
            stmt.setString(6, number.getText());
            stmt.setString(7, citta.getText());
            stmt.setString(8, cap.getText());

            stmt.setString(9, userData.getTaxCode());

            stmt.executeUpdate();

            ViewNavigator.navigateToProfilePaziente();

        } catch (Exception e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
        */

        PazienteModel.updateData(user);
    }

    private String arrotonda(String valore) {
        return new BigDecimal(valore)
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();
    }

    public boolean checkDati(){

        if(email.getText().isEmpty() || telephone.getText().isEmpty() || height.getText().isEmpty() || weight.getText().isEmpty() ||
        address.getText().isEmpty() || number.getText().isEmpty() || citta.getText().isEmpty()|| cap.getText().isEmpty()) {
            messaggioErrore("Uno o più campi sono vuoti!");
        }

        if(email.getText().isEmpty()) { emailError.setVisible(true); return false; }

        if(telephone.getText().isEmpty()) { telephoneError.setVisible(true); return false; }

        if(height.getText().isEmpty()) { heightError.setVisible(true); return false; }

        if(weight.getText().isEmpty()) { weightError.setVisible(true); return false; }

        if(address.getText().isEmpty()) { addressError.setVisible(true); return false; }

        if(number.getText().isEmpty()) { numberError.setVisible(true); return false; }

        if(citta.getText().isEmpty()) { cityError.setVisible(true); return false; }

        if(cap.getText().isEmpty()) { capError.setVisible(true); return false; }

        Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        if(!validEmail.matcher(email.getText()).matches()) { emailError.setVisible(true); return false;}

        Pattern validTelephone = Pattern.compile("^\\d{10}$");
        if(!validTelephone.matcher(telephone.getText()).matches()) { telephoneError.setVisible(true); return false;}

        Pattern validCap = Pattern.compile("^\\d{5}$");
        int capInt = Integer.parseInt(cap.getText());
        if(!validCap.matcher(cap.getText()).matches() && capInt > 0) { capError.setVisible(true); return false;}

        if (!number.getText().matches("\\d+([/]?[A-Za-z])?")) { messaggioErrore("number civico non è valido!"); return false; }

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



