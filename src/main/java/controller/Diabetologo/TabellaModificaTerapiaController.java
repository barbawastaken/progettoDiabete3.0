package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Diabetologo.TabellaModificaTerapiaModel;
import model.Diabetologo.Terapia;

public class TabellaModificaTerapiaController {

    @FXML private HBox navbarContainer;
    @FXML private final TabellaModificaTerapiaModel tabellaModificaTerapie = new TabellaModificaTerapiaModel();

    @FXML private TableView<Terapia> tabellaTerapie;
    @FXML private javafx.scene.control.TableColumn<Terapia, String> colTerapia;
    @FXML private TableColumn<Terapia, String> colFarmaco;
    @FXML private TableColumn<Terapia, String> colQuantita;
    @FXML private TableColumn<Terapia, String> colAssunzioni;
    @FXML private TableColumn<Terapia, String> colIndicazioni;
    @FXML private TableColumn<Terapia, String> colDataPrescrizione;

    private final ObservableList<Terapia> terapie = FXCollections.observableArrayList();
    private String taxCode;

    @FXML
    public void initialize() {

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO_fromVisualizzaTerapie);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        colTerapia.setCellValueFactory(new PropertyValueFactory<>("terapia"));
        colFarmaco.setCellValueFactory(new PropertyValueFactory<>("farmaco"));
        colQuantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        colAssunzioni.setCellValueFactory(new PropertyValueFactory<>("assunzioni"));
        colIndicazioni.setCellValueFactory(new PropertyValueFactory<>("indicazioni"));
        colDataPrescrizione.setCellValueFactory(new PropertyValueFactory<>("dataPrescrizione"));

        colTerapia.prefWidthProperty().bind(tabellaTerapie.widthProperty().multiply(0.2));
        colFarmaco.prefWidthProperty().bind(tabellaTerapie.widthProperty().multiply(0.15));
        colQuantita.prefWidthProperty().bind(tabellaTerapie.widthProperty().multiply(0.1));
        colAssunzioni.prefWidthProperty().bind(tabellaTerapie.widthProperty().multiply(0.1));
        colIndicazioni.prefWidthProperty().bind(tabellaTerapie.widthProperty().multiply(0.3));
        colDataPrescrizione.prefWidthProperty().bind(tabellaTerapie.widthProperty().multiply(0.15));

        tabellaTerapie.setRowFactory(terapiaTableView -> {

            TableRow<Terapia> row = new TableRow<>() {
                @Override
                protected void updateItem(Terapia item, boolean empty) {
                    super.updateItem(item, empty);

                    if(item == null || empty)
                        setStyle("");
                }
            };

            row.setOnMouseClicked(mouseEvent -> {
                if (!row.isEmpty() && mouseEvent.getClickCount() == 2) {
                    Terapia terapiaSelezionata = row.getItem();
                    Session.setSchermataDiArrivo("TABELLA_TERAPIE");
                    mostraModificaTerapia(terapiaSelezionata);
                }
            });

            return row;
        });

        this.taxCode = Session.getInstance().getPazienteInEsame().getTaxCode();
        loadTerapie();

    }

    private void loadTerapie() {
        terapie.setAll(tabellaModificaTerapie.getTerapieByTaxCode(taxCode));
        tabellaTerapie.setItems(terapie);
    }

    public void mostraModificaTerapia(Terapia terapia) {
        try {

            Session.setTerapiaInEsame(terapia);
            ViewNavigator.navigateToModificaTerapia();

        } catch (Exception e) {
            System.out.println("Errore caricamento schermata di modifica terapia");
        }
    }

    public void mostraModificaTerapiaFromStatistiche(Terapia terapia, String taxCode) {

        Session.getInstance().setPazienteInEsame(Session.getInfosOf(taxCode));
        System.out.println("Paziente impostato: " + Session.getInfosOf(taxCode).getTaxCode());
        mostraModificaTerapia(terapia);

    }
}
