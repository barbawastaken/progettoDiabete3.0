package view.Amministratore;

import controller.Amministratore.VisualizzaListaUtentiController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Amministratore.Utente;

public class VisualizzaListaUtentiView {
    private final TableView<Utente> tabellaUtenti = new TableView<>();

    public void start(Stage stage, VisualizzaListaUtentiController controller) {
        // Imposta dimensioni minime per la TableView
        tabellaUtenti.setMinWidth(800);
        tabellaUtenti.setMinHeight(400);

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
        colBirthDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate().toString()));

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
        colTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephoneNumber()));

        TableColumn<Utente, String> colUserType = new TableColumn<>("Tipo Utente");
        colUserType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserType()));

        TableColumn<Utente, String> colDiabetologo = new TableColumn<>("Diabetologo");
        colDiabetologo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiabetologo()));
        
        // Aggiunta delle colonne alla tabella
        tabellaUtenti.getColumns().addAll(
                colTaxCode, colPassword, colNome, colCognome, colEmail, colBirthDate,
                colAddress, colNumber, colCity, colCap, colGender, colTelephone,
                colUserType, colDiabetologo
        );

        // Imposta larghezza minima per ogni colonna
        tabellaUtenti.getColumns().forEach(col -> col.setMinWidth(100));

        // Carica gli utenti automaticamente
        controller.caricaUtenti(tabellaUtenti);

        // Layout e scena
        VBox layout = new VBox(10, tabellaUtenti);
        layout.setStyle("-fx-padding: 20");

        Scene scene = new Scene(layout, 1000, 600);

        // Stage setup
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setTitle("Visualizza Lista Utenti");
        stage.setScene(scene);
        stage.show();
    }
}
