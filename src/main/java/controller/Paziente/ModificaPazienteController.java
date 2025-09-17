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
import java.sql.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ModificaPazienteController {

    private final Session userData = Session.getInstance();

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    private boolean passwordChanged = false;
    private String newPassword;
    @FXML private HBox navbarContainer;
    HashMap<String, String> diabetologo = AggiungiUtenteModel.getDiabetologi();

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField taxCode;
    @FXML private TextField email;
    @FXML private TextField telephone;
    @FXML private DatePicker birthday;
    @FXML private Text gender;
    @FXML private TextField height;
    @FXML private TextField weight;
    @FXML private TextField address;
    @FXML private TextField number;
    @FXML private TextField citta;
    @FXML private TextField cap;
    @FXML private TextField nation;
    @FXML private Text medicoCurante;

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

        numberError.setVisible(false);
        telephoneError.setVisible(false);
        emailError.setVisible(false);
        capError.setVisible(false);
        weightError.setVisible(false);
        heightError.setVisible(false);
        userAddedText.setVisible(false);
        passwordError.setVisible(false);
        nationError.setVisible(false);
        cityError.setVisible(false);
        addressError.setVisible(false);

        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        Session.getInfos();

        this.nome.setText(userData.getNome());
        this.cognome.setText(userData.getCognome());
        this.taxCode.setText(userData.getTaxCode());
        this.email.setText(userData.getEmail());
        this.telephone.setText(userData.getTelephone());
        this.birthday.setValue(Date.valueOf(userData.getBirthday()).toLocalDate());
        this.gender.setText(userData.getSex());
        this.citta.setText(userData.getCity());
        this.address.setText(userData.getAddress());
        this.cap.setText(userData.getCap().toString());
        this.number.setText(userData.getCivic());
        this.weight.setText(Double.toString(userData.getWeight()));
        this.height.setText(Double.toString(userData.getHeight()));


        for(String s : diabetologo.keySet()){
            if(diabetologo.get(s).equals(userData.getMedicoCurante()))
                this.medicoCurante.setText(s);
        }

        //this.password.setText(userData.getPassword());
        this.nation.setText(userData.getCountryOfResidence());

        cap.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });

        telephone.addEventFilter(KeyEvent.KEY_TYPED, event -> {
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
                this.gender.getText(),
                this.telephone.getText(),
                "PAZIENTE",
                diabetologo.get(this.medicoCurante.getText()),
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

        if(passwordChanged)
            ViewNavigator.relogPaziente(taxCode.getText(), newPassword);
        else
            ViewNavigator.relogPaziente(taxCode.getText(), Session.getInstance().getPassword());

    }

    public boolean checkDati(){

        if(email.getText().isEmpty() || telephone.getText().isEmpty() || height.getText().isEmpty() || weight.getText().isEmpty() ||
        address.getText().isEmpty() || number.getText().isEmpty() || citta.getText().isEmpty()|| cap.getText().isEmpty()) {
            messaggioErrore("Uno o più campi sono vuoti!");
        }

        boolean flag = true;
        if(email.getText().isEmpty()) { emailError.setVisible(true); flag = false; }
        else{emailError.setVisible(false);}

        if(weight.getText().isEmpty()) {weightError.setVisible(true); flag = false; }
        else{weightError.setVisible(false); }

        if(height.getText().isEmpty()) {heightError.setVisible(true); flag = false; }
        else{heightError.setVisible(false); }

        if(telephone.getText().isEmpty()) { telephoneError.setVisible(true); flag = false; }
        else{telephoneError.setVisible(false); }

        if(address.getText().isEmpty()) { addressError.setVisible(true); flag = false; }
        else{addressError.setVisible(false); }

        if(number.getText().isEmpty()) { numberError.setVisible(true); flag = false; }
        else{numberError.setVisible(false); }

        if(citta.getText().isEmpty()) { cityError.setVisible(true); flag = false; }
        else{cityError.setVisible(false); }

        if(cap.getText().isEmpty()) { capError.setVisible(true); flag = false; }
        else{capError.setVisible(false); }

        if(nation.getText().isEmpty()) {nationError.setVisible(true); flag = false; }
        else{nationError.setVisible(false); }

        Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        if(!validEmail.matcher(email.getText()).matches()) { emailError.setVisible(true); flag = false;}
        else{emailError.setVisible(false); }

        Pattern validTelephone = Pattern.compile("^\\d{10}$");
        if(!validTelephone.matcher(telephone.getText()).matches()) { telephoneError.setVisible(true); flag = false;}
        else{telephoneError.setVisible(false); }

        Pattern validCap = Pattern.compile("^\\d{5}$");
        int capInt = 0;
        if(!cap.getText().isEmpty())
            capInt = Integer.parseInt(cap.getText());
        if(!validCap.matcher(cap.getText()).matches() && capInt > 0) { capError.setVisible(true); flag = false;}
        else{capError.setVisible(false); }

        if (!number.getText().matches("\\d+([/]?[A-Za-z])?")) { messaggioErrore("number civico non è valido!"); flag = false; }

        return flag;
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

        if(nuovaPasswordField.getText().isEmpty() || confermaPasswordField.getText().isEmpty()){
            messaggioErrore("I campi della password devono essere compilati");
            return;
        }

        this.newPassword = nuovaPasswordField.getText();
        this.passwordChanged = true;
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



