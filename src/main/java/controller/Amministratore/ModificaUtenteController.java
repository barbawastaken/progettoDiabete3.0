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
    private ToggleGroup ruolo=new ToggleGroup();
    private VisualizzaListaUtentiController listaUtentiController;


    public ModificaUtenteController() { }


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


        //System.out.println("Nazione in AGGIORNATO: " + aggiornato.getNation());
        /*
        ho perso mezza giornata di lavoro perché davanti a 'peso' a una tab di distanza c'era un meno che mi faceva fallire
        il check di sql. Questo commento rimarrà qui per gli annali
         */
        //System.out.println("!!!!!!!!!" + taxCode.getText() + "!!!!!!!!!!!!");

        /*
        modificaUtenteModel.aggiornaUtente(utente.getTaxCode(), aggiornato);

        if(listaUtentiController != null){ listaUtentiController.aggiornaTabellaUtenti();}


        if(!check()) {
            System.out.println("un casino");

            Stage currentStage = (Stage) nome.getScene().getWindow();
            currentStage.close();
        } else{
            System.out.println("casino");
        }
        */


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
    public void setListaUtentiController(VisualizzaListaUtentiController controller){ this.listaUtentiController = controller; }
}

