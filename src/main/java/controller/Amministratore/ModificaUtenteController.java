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

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class ModificaUtenteController{
    private ModificaUtenteView modificaUtenteView;
    private Utente utente;
    private  VisualizzaListaUtentiModel model;
    private  ModificaUtenteModel modificaUtenteModel;
    private Stage listaUtentiStage;
    private ToggleGroup ruolo;
    private VisualizzaListaUtentiController listaUtentiController;


    @FXML  private ComboBox<String> gender;
    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private TextField address;
    @FXML private TextField citta;
    @FXML private TextField number;
    @FXML private TextField taxCode;
    @FXML private RadioButton paziente;
    @FXML private RadioButton diabetologo;
    @FXML private RadioButton amministratore;
    @FXML private ComboBox<String> medicoCurante;
    @FXML private TextField cap;
    @FXML private DatePicker birthday;
    @FXML private TextField telephone;
    @FXML private Text taxCodeError;
    @FXML private Text numberError;
    @FXML private Text telephoneError;
    @FXML private Text emailError;
    @FXML private Text capError;
    @FXML private Text birthdayError;
    @FXML private Text genderError;
    @FXML private Text medicoCuranteText;
    @FXML private Text userAddedText;
    @FXML private TextField nation;
    @FXML private TextField weight;
    @FXML private TextField height;

    public ModificaUtenteController(){ }


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
        userAddedText.setVisible(false);
        userAddedText.setManaged(false);

        gender.getItems().addAll("Maschio", "Femmina");
        ruolo=new ToggleGroup();
        paziente.setToggleGroup(ruolo);
        diabetologo.setToggleGroup(ruolo);
        amministratore.setToggleGroup(ruolo);
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
        height.setText(String.valueOf(utente.getHeight()));
        weight.setText(String.valueOf(utente.getWeight()));
        number.setText(String.valueOf(utente.getNumber()));
        citta.setText(utente.getCity());
        nation.setText(utente.getCountryOfResidence());
        birthday.setValue(LocalDate.now());



        if(utente.getRole().equals("DIABETOLOGO")){
            diabetologo.setSelected(true);
        } else if(utente.getRole().equals("AMMINISTRATORE")){
            amministratore.setSelected(true);

        }else{
            paziente.setSelected(true);
            medicoCurante.setVisible(true);
            medicoCurante.setManaged(true);
            medicoCuranteText.setVisible(true);
            medicoCuranteText.setManaged(true);
            medicoCurante.setValue(utente.getDiabetologo());

        }
    }

    private boolean isEmailValid(){
        Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        //[\\w.-] -> campo "libero", con lettere, punti e trattini
        //@ -> simbolo obbligatorio
        //\\.\\w{2,}$ ->almeno due caratteri preceduti da un punto

        return validEmail.matcher(email.getText()).matches();
    }

    private boolean isTelephoneValid(){
        Pattern validTelephone = Pattern.compile("^\\d{10}$");

        return validTelephone.matcher(telephone.getText()).matches();
    }

    private boolean isCapValid(){
        Pattern validCap = Pattern.compile("^\\d{5}$");

        return validCap.matcher(cap.getText()).matches();
    }

    private boolean isNumberValid(){
        Pattern validNumber = Pattern.compile("^\\d{1,3}$");
        return validNumber.matcher(number.getText()).matches();
    }

    @FXML
    private void onIndietroButtonPressed(){
        ((Stage) nome.getScene().getWindow()).close();
    }

    @FXML
    private void onSendButtonPressed(){
        try{
            RadioButton selected = (RadioButton) ruolo.getSelectedToggle();
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
                    selected.getText(),
                    medicoCurante.getValue(),
                    Double.parseDouble(weight.getText()),
                    Double.parseDouble(height.getText())
            );

            modificaUtenteModel.aggiornaUtente(utente.getTaxCode(), aggiornato);

            Stage currentStage = (Stage) nome.getScene().getWindow();
            currentStage.close();

            listaUtentiStage.close();
            VisualizzaListaUtentiView nuovaView = new VisualizzaListaUtentiView();
            new VisualizzaListaUtentiController(model,nuovaView,currentStage);
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante il salvataggio. Controlla i dati.");
            alert.showAndWait();
        }

    }


    public ModificaUtenteController(ModificaUtenteView modificaUtenteView, Utente utente, VisualizzaListaUtentiModel model, Stage listaUtentiStage) {
        this.modificaUtenteView = modificaUtenteView;
        this.utente = utente;
        this.model = model;
        this.listaUtentiStage = listaUtentiStage;
    }


    public ModificaUtenteController(ModificaUtenteModel modificaUtenteModel, ModificaUtenteView modificaUtenteView, Utente selezionato, Stage visualizzaUtentiStage){

        this.modificaUtenteView = modificaUtenteView;
        this.modificaUtenteModel = modificaUtenteModel;
        this.utente = selezionato;
        this.listaUtentiStage = visualizzaUtentiStage;

        //Utente utenteModificato = this.utenteModificato();
        //modificaUtenteView.getSalvaButton().setOnAction(e -> modificaUtenteModel.aggiornaUtente(selezionato.getTaxCode(), utente));

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

