package controller.Diabetologo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Diabetologo.TabellaModificaTerapiaModel;
import model.Diabetologo.Terapia;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabellaModificaTerapiaController implements Initializable {

    private String taxCode;
    private String nomeTerapia;
    @FXML
    private final TabellaModificaTerapiaModel tabellaModificaTerapie = new TabellaModificaTerapiaModel();

    @FXML private TableView<Terapia> tabellaTerapie;
    //@FXML private TableColumn<Terapia, String> taxCodeColumn;
    @FXML private javafx.scene.control.TableColumn<Terapia, String> colTerapia;
    @FXML private TableColumn<Terapia, String> colFarmaco;
    @FXML private TableColumn<Terapia, String> colQuantita;
    @FXML private TableColumn<Terapia, String> colAssunzioni;
    @FXML private TableColumn<Terapia, String> colIndicazioni;

    private final ObservableList<Terapia> terapie = FXCollections.observableArrayList();
    private String taxCodeDiabetologo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTerapia.setCellValueFactory(new PropertyValueFactory<>("terapia"));
        colFarmaco.setCellValueFactory(new PropertyValueFactory<>("farmaco"));
        colQuantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        colAssunzioni.setCellValueFactory(new PropertyValueFactory<>("assunzioni"));
        colIndicazioni.setCellValueFactory(new PropertyValueFactory<>("indicazioni"));

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
                    mostraModificaTerapia(terapiaSelezionata);
                }
            });

            return row;
        });
    }

    public void setTaxCode(String taxCode, String taxCodeDiabetologo) {
        this.taxCode = taxCode;
        this.nomeTerapia = colTerapia.getText();
        this.taxCodeDiabetologo = taxCodeDiabetologo;
        loadTerapie();
    }

    private void loadTerapie() {
        terapie.setAll(tabellaModificaTerapie.getTerapieByTaxCode(taxCode, nomeTerapia));
        tabellaTerapie.setItems(terapie);
    }

    private void mostraModificaTerapia(Terapia terapia) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/modifica_terapia_view.fxml"));
            Parent root = loader.load();

            ModificaTerapiaController controller = loader.getController();
            controller.setTaxCode(taxCode, taxCodeDiabetologo);
            controller.inizialize();

            Stage stage = new Stage();
            stage.setTitle("Modifica Terapia");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
