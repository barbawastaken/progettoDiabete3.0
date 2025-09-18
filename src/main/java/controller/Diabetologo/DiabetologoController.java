package controller.Diabetologo;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Diabetologo.DiabetologoModel;
import model.Diabetologo.Terapia;
import model.NotificationModel;
import java.sql.SQLException;
import java.time.LocalDate;

public class DiabetologoController {

    @FXML private Button statisticheButton;
    @FXML private HBox navbarContainer;

    @FXML private TableView<Notifica> notifichePerFarmaci;
    @FXML private TableColumn<Notifica, String> pazienteFarmaco;
    @FXML private TableColumn<Notifica, String> farmacoFarmaco;
    @FXML private TableColumn<Notifica, String> notificaFarmaco;

    @FXML private TableView<Notifica> notifichePerGlicemia;
    @FXML private TableColumn<Notifica, String> pazienteGlicemia;
    @FXML private TableColumn<Notifica, String> notificaGlicemia;
    @FXML private TableColumn<Notifica, LocalDate> dataGlicemia;


    @FXML
    public void initialize() throws SQLException {

        Session.getInfos();

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        if(Session.getInfosOf(Session.getInstance().getTaxCode()).getRole().equals("DIABETOLOGO")){
            this.statisticheButton.setDisable(true);
        }

        pazienteFarmaco.setCellValueFactory(new PropertyValueFactory<>("pazienteFarmaco"));
        farmacoFarmaco.setCellValueFactory(new PropertyValueFactory<>("farmacoFarmaco"));
        notificaFarmaco.setCellValueFactory(new PropertyValueFactory<>("notificaFarmaco"));

        pazienteFarmaco.prefWidthProperty().bind(notifichePerFarmaci.widthProperty().multiply(0.20));
        farmacoFarmaco.prefWidthProperty().bind(notifichePerFarmaci.widthProperty().multiply(0.30));
        notificaFarmaco.prefWidthProperty().bind(notifichePerFarmaci.widthProperty().multiply(0.50));

        pazienteGlicemia.setCellValueFactory(new PropertyValueFactory<>("pazienteGlicemia"));
        notificaGlicemia.setCellValueFactory(new PropertyValueFactory<>("notificaGlicemia"));
        dataGlicemia.setCellValueFactory(new PropertyValueFactory<>("dataGlicemia"));

        pazienteGlicemia.prefWidthProperty().bind(notifichePerGlicemia.widthProperty().multiply(0.20));
        notificaGlicemia.prefWidthProperty().bind(notifichePerGlicemia.widthProperty().multiply(0.40));
        dataGlicemia.prefWidthProperty().bind(notifichePerGlicemia.widthProperty().multiply(0.40));

        NotificationModel notificationModel = new NotificationModel(Session.getInstance().getTaxCode());
        notifichePerFarmaci.setItems(notificationModel.notifyRitardoAssunzioni());
        notifichePerGlicemia.setItems(notificationModel.notifyGlicemia());

        notifichePerFarmaci.setRowFactory(tv -> {
            TableRow<Notifica> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // doppio click
                    Notifica notificaSelezionata = row.getItem();

                    String farmacoNotifica = notificaSelezionata.getFarmacoFarmaco();

                    DiabetologoModel model = new DiabetologoModel();
                    String taxCodePaziente = model.getTaxCodeFromPaziente(notificaSelezionata.getNomePazienteFarmaco(), notificaSelezionata.getCognomePazienteFarmaco());
                    Terapia terapiaDaModificare = model.getTerapia(taxCodePaziente, farmacoNotifica);

                    // Qui apri la nuova schermata
                    TabellaModificaTerapiaController controller = new TabellaModificaTerapiaController();
                    Session.setSchermataDiArrivo("NOTIFICHE");
                    controller.mostraModificaTerapiaFromStatistiche(terapiaDaModificare, taxCodePaziente);
                }
            });
            return row;
        });

        notifichePerGlicemia.setRowFactory(tv -> {
            TableRow<Notifica> row = new TableRow<>() {
                @Override
                protected void updateItem(Notifica notifica, boolean empty) {
                    super.updateItem(notifica, empty);

                    if (empty || notifica == null) {
                        setStyle(""); // Rimuovi lo stile per le righe vuote
                    } else {
                        // Applica il colore come sfondo
                        setStyle("-fx-background-color: " + notifica.getColore() + ";");
                    }
                }
            };

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // doppio click
                    Notifica notificaSelezionata = row.getItem();

                    String nomePazienteGlicemia = notificaSelezionata.getNomePazienteGlicemia();
                    String cognomePazienteGlicemia = notificaSelezionata.getCognomePazienteGlicemia();

                    DiabetologoModel model = new DiabetologoModel();
                    String taxCodePaziente = model.getTaxCodeFromPaziente(nomePazienteGlicemia, cognomePazienteGlicemia);

                    Session.getInstance().setPazienteInEsame(Session.getInfosOf(taxCodePaziente));

                    Session.setSchermataDiArrivo("NOTIFICHE");
                    ViewNavigator.navigateToPatientDetails();
                }

            });

            return row;
        });

    }

    @FXML public void isVisualizzaPazientiClicked() {

        ViewNavigator.navigateToVisualizzaPazienti();

    }

    @FXML public void isVisualizzaStatisticheClicked(){

        ViewNavigator.navigateToVisualizzaStatistiche();

    }

}
