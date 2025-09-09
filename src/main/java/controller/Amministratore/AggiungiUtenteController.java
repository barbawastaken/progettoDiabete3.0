package controller.Amministratore;

import controller.LoginController;
import controller.NavBar;
import controller.NavBarTags;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Amministratore.AggiungiUtenteModel;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;


public class AggiungiUtenteController extends GestioneUtenti {


    private ToggleGroup ruolo;
    private HashMap<String, String> diabetologi = new HashMap<>();
    //TOP BAR IN COMUNE CIN MODIFICA UTENTE




    @FXML private DatePicker birthday;

    @FXML private HBox navbarContainer;

    @FXML
    private void initialize() throws SQLException {



        AggiungiUtenteModel model = new AggiungiUtenteModel();

        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE_toHomepage);


        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        gender.getItems().addAll("Maschio", "Femmina");
        medicoCurante.getItems().addAll();
        ruolo = new ToggleGroup();
        paziente.setToggleGroup(ruolo);
        diabetologo.setToggleGroup(ruolo);
        amministratore.setToggleGroup(ruolo);

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
        medicoCurante.setVisible(false);
        medicoCurante.setManaged(false);
        medicoCuranteText.setVisible(false);
        medicoCuranteText.setManaged(false);
        userAddedText.setVisible(false);
        userAddedText.setManaged(false);
        weight.setVisible(false);
        weight.setManaged(false);
        height.setVisible(false);
        height.setManaged(false);
        heightText.setVisible(false);
        heightText.setManaged(false);
        weightText.setVisible(false);
        weightText.setManaged(false);
        heightError.setVisible(false);
        heightError.setManaged(false);
        weightError.setVisible(false);
        weightError.setManaged(false);
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



        ruolo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null) {
                RadioButton radioButton = (RadioButton) newValue;
                String val = radioButton.getText();
                if ("PAZIENTE".equals(val)) {

                    try {
                        HashMap<String, String> map = model.getDiabetologi();
                        for(String s : map.keySet()){
                            medicoCurante.getItems().add(s);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    medicoCuranteText.setVisible(true);
                    medicoCuranteText.setManaged(true);
                    medicoCurante.setVisible(true);
                    medicoCurante.setManaged(true);
                    weight.setVisible(true);
                    weight.setManaged(true);
                    height.setVisible(true);
                    height.setManaged(true);
                    weightText.setVisible(true);
                    weightText.setManaged(true);
                    heightText.setVisible(true);
                    heightText.setManaged(true);


                } else {
                    medicoCuranteText.setVisible(false);
                    medicoCuranteText.setManaged(false);
                    medicoCurante.setVisible(false);
                    medicoCurante.setManaged(false);
                    medicoCurante.getItems().clear();
                    weight.setVisible(false);
                    weight.setManaged(false);
                    height.setVisible(false);
                    height.setManaged(false);
                    weightText.setVisible(false);
                    weightText.setManaged(false);
                    heightText.setVisible(false);
                    heightText.setManaged(false);
                    heightError.setVisible(false);
                    weightError.setVisible(false);
                    heightError.setManaged(false);
                    weightError.setManaged(false);
                    //medicoCurante.setValue(null);
                    //height.setText(null);
                    //weight.setText(null);
                }
            }
        });

        diabetologi = model.getDiabetologi();

    }



    @FXML
    private void resetButtonPressed(){
        nome.setText("");
        cognome.setText("");
        email.setText("");
        password.setText("");
        address.setText("");
        citta.setText("");
        taxCode.setText("");
        address.setText("");
        cap.setText("");

    }

    @FXML
    private void sendButtonPressed() throws SQLException {
        AggiungiUtenteModel model = new AggiungiUtenteModel();
        RadioButton selected = (RadioButton) ruolo.getSelectedToggle();
        boolean isPaziente = selected != null && selected.getText().equals("PAZIENTE");

        boolean hasErrors;
        if (isPaziente) {
            hasErrors = checkForPazienti();
        } else {
            hasErrors = check();
        }

        if (!hasErrors) {
            try {
                if (isPaziente) {
                    model.inserisciUtente(
                            taxCode.getText(), password.getText(), nome.getText(), cognome.getText(), address.getText(),
                            cap.getText(), citta.getText(), email.getText(), gender.getValue(),
                            java.sql.Date.valueOf(birthday.getValue()), number.getText(), telephone.getText(),
                            selected.getText(), medicoCurante.getValue(), nation.getText(), height.getText(), weight.getText());
                } else {
                    model.inserisciUtente(
                            taxCode.getText(), password.getText(), nome.getText(), cognome.getText(), address.getText(),
                            cap.getText(), citta.getText(), email.getText(), gender.getValue(),
                            java.sql.Date.valueOf(birthday.getValue()), number.getText(), telephone.getText(),
                            selected.getText(), null, nation.getText(),null, null); // valori nulli per non pazienti
                }
                //Chiudo finestra attuale gg

                //Apro lista utenti(si spera aggiornata)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/visualizza_utenti_view.fxml"));
                Parent root = loader.load();
                //Parte nome utente evidenziato
                VisualizzaListaUtentiController controller = loader.getController();
                controller.evidenziaUtente(taxCode.getText());

                ViewNavigator.navigateToVisualizzaUtenti();

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // PARTE AGGIUNTA DA ME (ANDREA) 11 LUGLIO PER BARRA BLU CON LOGOUT E HOME

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

    public AggiungiUtenteController(){ }
}
