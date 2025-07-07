package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amministratore.ModificaUtenteModel;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.ModificaUtenteView;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class ModificaUtenteController extends GestioneUtenti{
    private ModificaUtenteView modificaUtenteView;
    private Utente utente;
    private  VisualizzaListaUtentiModel model;
    private  ModificaUtenteModel modificaUtenteModel;
    private Stage listaUtentiStage;
    private ToggleGroup ruolo;
    private VisualizzaListaUtentiController listaUtentiController;


    public ModificaUtenteController() { }

    @FXML
    private void initialize(){

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
        weightError.setVisible(false);
        weightError.setManaged(false);
        heightError.setVisible(false);
        heightError.setManaged(false);
        heightText.setVisible(false);
        heightText.setManaged(false);
        weightText.setVisible(false);
        weightText.setManaged(false);
        userAddedText.setVisible(false);
        userAddedText.setManaged(false);
        weight.setVisible(false);
        weight.setManaged(false);
        height.setVisible(false);
        height.setManaged(false);
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

        gender.getItems().addAll("Maschio", "Femmina");
        ruolo=new ToggleGroup();
    }
    public void initializeData(VisualizzaListaUtentiController listaUtentiController, Utente utente, VisualizzaListaUtentiModel model, ModificaUtenteModel modificaUtenteModel,
                               Stage listaUtentiStage) {

        this.listaUtentiController = listaUtentiController;
        this.utente = utente;
        this.model = model;
        this.modificaUtenteModel = modificaUtenteModel;
        this.listaUtentiStage = listaUtentiStage;

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
        //super.check();
        String ruoloSelezionato = utente.getRole();
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
           -     peso,
                altezza
        );

        System.out.println("Peso in 'AGGIORNATO': " + peso);
        System.out.println("Altezza in 'AGGIORNATO': " + altezza);
        //System.out.println("!!!!!!!!!" + taxCode.getText() + "!!!!!!!!!!!!");
        modificaUtenteModel.aggiornaUtente(utente.getTaxCode(), aggiornato);

        if(listaUtentiController != null){ listaUtentiController.aggiornaTabellaUtenti();}
        if(!check()) {
            Stage currentStage = (Stage) nome.getScene().getWindow();
            currentStage.close();
        }
    }
    public void setUtente(Utente utente){ this.utente = utente; }
    public void setListaUtentiController(VisualizzaListaUtentiController controller){ this.listaUtentiController = controller; }
}

