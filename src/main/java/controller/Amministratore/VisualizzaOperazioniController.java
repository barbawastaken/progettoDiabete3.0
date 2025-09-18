package controller.Amministratore;

import controller.NavBar;
import controller.NavBarTags;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaOperazioniController {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    @FXML private HBox navbarContainer;

    @FXML private TableView<Operazione> tabellaOperazioni;
    @FXML private TableColumn<Operazione, String> taxCodeDiabetologo;
    @FXML private TableColumn<Operazione, String> operazione;
    @FXML private TableColumn<Operazione, String> taxCodePaziente;
    @FXML private TableColumn<Operazione, String> dataOperazione;

    @FXML private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        taxCodeDiabetologo.setCellValueFactory(new PropertyValueFactory<>("taxCodeDiabetologo"));
        operazione.setCellValueFactory(new PropertyValueFactory<>("operazione"));
        taxCodePaziente.setCellValueFactory(new PropertyValueFactory<>("taxCodePaziente"));
        dataOperazione.setCellValueFactory(new PropertyValueFactory<>("dataOperazione"));

        taxCodeDiabetologo.prefWidthProperty().bind(tabellaOperazioni.widthProperty().multiply(0.1));
        operazione.prefWidthProperty().bind(tabellaOperazioni.widthProperty().multiply(0.6));
        taxCodePaziente.prefWidthProperty().bind(tabellaOperazioni.widthProperty().multiply(0.1));
        dataOperazione.prefWidthProperty().bind(tabellaOperazioni.widthProperty().multiply(0.2));

        List<Operazione> operazioni = caricaOperazioni();
        if(operazioni != null) {
            tabellaOperazioni.setItems(FXCollections.observableList(operazioni)); }
    }

    private List<Operazione> caricaOperazioni() {

        String query = "SELECT * FROM logTable ORDER BY data DESC";

        try(Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(query);
            List<Operazione> operazioni = new ArrayList<>();

            while(rs.next()){

                Operazione operazione = new Operazione(
                        rs.getString("taxCodeDiabetologo"),
                        rs.getString("operazione"),
                        rs.getString("taxCodePaziente"),
                        rs.getString("data")
                );

                operazioni.add(operazione);
            }

            return operazioni;

        } catch (Exception e) {
            System.out.println("Errore caricamento operazioni dalla logTable: " + e.getMessage());
            return null;
        }

    }

}
