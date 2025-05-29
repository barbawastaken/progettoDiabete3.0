package view.Amministratore;

import controller.Amministratore.VisualizzaListaUtentiController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Amministratore.Utente;

import java.util.ArrayList;
import java.util.List;

public class VisualizzaListaUtentiView {
    private final List<Utente> tabellaUtenti = new ArrayList<>();
    private final Button indietroButton = new Button("Indietro");

    private Stage stage;

    public void start(Stage stage, VisualizzaListaUtentiController controller) {
        this.stage=stage;

        // Imposta dimensioni minime per la TableView


        // Creazione colonne della tabella
        TableColumn<Utente, String> colTaxCode = new TableColumn<>("Codice Fiscale");
        colTaxCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaxCode()));

        TableColumn<Utente, String> colPassword = new TableColumn<>("Password");
        colPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));

        TableColumn<Utente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        TableColumn<Utente, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCognome()));

        TableColumn<Utente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<Utente, String> colBirthDate = new TableColumn<>("Data di Nascita");
        colBirthDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthday().toString()));

        TableColumn<Utente, String> colAddress = new TableColumn<>("Indirizzo");
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        TableColumn<Utente, Number> colNumber = new TableColumn<>("Numero Civico");
        colNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()));

        TableColumn<Utente, String> colCity = new TableColumn<>("Città");
        colCity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));

        TableColumn<Utente, Number> colCap = new TableColumn<>("CAP");
        colCap.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCap()));

        TableColumn<Utente, String> colGender = new TableColumn<>("Genere");
        colGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));

        TableColumn<Utente, String> colTelephone = new TableColumn<>("Telefono");
        colTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

        TableColumn<Utente, String> colUserType = new TableColumn<>("Tipo Utente");
        colUserType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));

        TableColumn<Utente, String> colDiabetologo = new TableColumn<>("Diabetologo");
        colDiabetologo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiabetologo()));
        

        // Carica gli utenti automaticamente
        //controller.caricaUtenti(tabellaUtenti);

    }

    public Button getIndietroButton() { return indietroButton;}

    public Stage getStage(){ return stage;}
}
