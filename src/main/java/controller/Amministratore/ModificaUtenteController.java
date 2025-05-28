package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amministratore.ModificaUtenteModel;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.ModificaUtenteView;
import view.Amministratore.VisualizzaListaUtentiView;

import java.time.LocalDate;

public class ModificaUtenteController {
    private ModificaUtenteView modificaUtenteView;
    private Utente utente;
    private  VisualizzaListaUtentiModel model;
    private  ModificaUtenteModel modificaUtenteModel;
    private Stage listaUtentiStage;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField address;

    @FXML
    private TextField citta;
    @FXML
    private TextField number;

    @FXML
    private TextField taxCode;

    @FXML
    private RadioButton paziente;

    @FXML
    private RadioButton diabetologo;

    @FXML
    private RadioButton amministratore;

    @FXML
    private ComboBox<String> medicoCurante;

    @FXML
    private TextField cap;

    @FXML
    private DatePicker birthday;

    @FXML
    private TextField telephone;

    @FXML
    private Text taxCodeError;

    @FXML
    private Text numberError;

    @FXML
    private Text telephoneError;

    @FXML
    private Text emailError;

    @FXML
    private Text capError;

    @FXML
    private Text birthdayError;

    @FXML
    private Text genderError;

    @FXML
    private Text medicoCuranteText;

    @FXML
    private Text userAddedText;

    @FXML
    private void initialize(){
        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        email.setText(utente.getEmail());
        password.setText(utente.getPassword());
        address.setText(utente.getAddress());
        birthday.setValue(LocalDate.parse(utente.getBirthday().toString()));
        gender.setValue(utente.getGender());
        telephone.setText(utente.getTelephone());
        taxCode.setText(utente.getTaxCode());
        cap.setText(String.valueOf(utente.getCap()));



    }


    public ModificaUtenteController(ModificaUtenteView modificaUtenteView, Utente utente, VisualizzaListaUtentiModel model, Stage listaUtentiStage) {
        this.modificaUtenteView = modificaUtenteView;
        this.utente = utente;
        this.model = model;
        this.listaUtentiStage = listaUtentiStage;
    }

    public ModificaUtenteController(){

    }
    public ModificaUtenteController(ModificaUtenteModel modificaUtenteModel, ModificaUtenteView modificaUtenteView, Utente selezionato, Stage visualizzaUtentiStage){

        this.modificaUtenteView = modificaUtenteView;
        this.modificaUtenteModel = modificaUtenteModel;
        this.utente = selezionato;
        this.listaUtentiStage = visualizzaUtentiStage;

        //Utente utenteModificato = this.utenteModificato();
        modificaUtenteView.getSalvaButton().setOnAction(e -> modificaUtenteModel.aggiornaUtente(selezionato.getTaxCode(), modificaUtenteView, listaUtentiStage, utente));

    }

    private Utente utenteModificato() {
        return new Utente(
                modificaUtenteView.getTaxCode(),
                modificaUtenteView.getPassword(),
                modificaUtenteView.getNome(),
                modificaUtenteView.getCognome(),
                modificaUtenteView.getEmail(),
                java.sql.Date.valueOf(modificaUtenteView.getBirthday()),
                modificaUtenteView.getAddress(),
                modificaUtenteView.getNumber(),
                modificaUtenteView.getCity(),
                modificaUtenteView.getCap(),
                modificaUtenteView.getCountryOfResidence(),
                modificaUtenteView.getGender(),
                modificaUtenteView.getTelephone(),
                modificaUtenteView.getRole(),
                modificaUtenteView.getDiabetologo(),
                modificaUtenteView.getWeight(),
                modificaUtenteView.getHeight()
        );

    }

    public void salvaModifiche(Stage stage) {
        try {
            // Crea oggetto aggiornato
            Utente aggiornato = new Utente(
                    modificaUtenteView.getTaxCode(),
                    modificaUtenteView.getPassword(),
                    modificaUtenteView.getNome(),
                    modificaUtenteView.getCognome(),
                    modificaUtenteView.getEmail(),
                    java.sql.Date.valueOf(modificaUtenteView.getBirthday()),
                    modificaUtenteView.getAddress(),
                    modificaUtenteView.getNumber(),
                    modificaUtenteView.getCity(),
                    modificaUtenteView.getCap(),
                    modificaUtenteView.getCountryOfResidence(),
                    modificaUtenteView.getGender(),
                    modificaUtenteView.getTelephone(),
                    modificaUtenteView.getRole(),
                    modificaUtenteView.getDiabetologo(),
                    modificaUtenteView.getWeight(),
                    modificaUtenteView.getHeight()
            );

            //System.out.println(modificaUtenteView.getTaxCode());

            // Salva nel DB tramite il model
            model.aggiornaUtente(aggiornato, modificaUtenteView.getTaxCode());
            System.out.println("Utente aggiornato con successo.");
            modificaUtenteView.getStage().close();

            // Ritorna alla lista utenti, nello stesso stage
            this.listaUtentiStage.close();
            VisualizzaListaUtentiView nuovaView = new VisualizzaListaUtentiView();
            VisualizzaListaUtentiController nuovoController = new VisualizzaListaUtentiController(model, nuovaView, stage);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante il salvataggio");
            alert.showAndWait();
        }
    }


    public void setUtente(Utente utente){
        this.utente = utente;
    }
}

