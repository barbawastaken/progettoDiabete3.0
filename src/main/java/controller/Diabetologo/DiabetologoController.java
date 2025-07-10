package controller.Diabetologo;

import controller.Amministratore.VisualizzaListaUtentiController;
import controller.LoginController;
import controller.Paziente.ModificaPazienteController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import model.Diabetologo.DiabetologoModel;
import model.NotificationModel;
import view.Diabetologo.DiabetologoView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiabetologoController {

    private DiabetologoModel diabetologoModel;
    private DiabetologoView diabetologoView;
    private String taxCode;
    @FXML private HBox topBar;
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
    public DiabetologoController() { }

    public void initializeData(String taxCode) {
        this.taxCode = taxCode;
    }

    @FXML
    private void onLogoutPressed(){

        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setTaxCode(taxCode);
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }

        Stage diabetologoStage = (Stage)topBar.getScene().getWindow();
        diabetologoStage.close();

    }

    @FXML
    public void isVisualizzaPazientiClicked() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/visualizza_pazienti_view.fxml"));
            Parent root = loader.load();
            //VisualizzaListaUtentiController

            VisualizzaPazientiController controller = loader.getController();
            controller.setTaxCode(taxCode);
            Stage stage = new Stage();
            stage.setTitle("Visualizza Pazienti");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void isVisualizzaNotificheClicked() {

    }

    @FXML
    public void initialize() throws SQLException {
        NotificationModel notificationModel = new NotificationModel(taxCode);
        ArrayList<String> results = notificationModel.notifyRitardo();
        System.out.println(results.toString());
    }

    public DiabetologoController(DiabetologoModel diabetologoModel, DiabetologoView diabetologoView, Stage loginStage){

        this.diabetologoModel = diabetologoModel;
        this.diabetologoView = diabetologoView;

        Stage diabetologoStage = new Stage();
        diabetologoStage.setScene(diabetologoView.getScene());
        diabetologoStage.setHeight(loginStage.getHeight());
        diabetologoStage.setWidth(loginStage.getWidth());
        diabetologoStage.setX(loginStage.getX());
        diabetologoStage.setY(loginStage.getY());
        diabetologoStage.alwaysOnTopProperty();
        diabetologoStage.setMinHeight(320);
        diabetologoStage.setMinWidth(240);
        diabetologoStage.setTitle("Homepage diabetologo");
        loginStage.close();
        diabetologoStage.show();
    }

    public void setTaxCode(){
        this.taxCode = taxCode;
    }

    @FXML
    private void onProfiloClicked(){
        try{
            Stage profiloDiabetologo = new Stage();
            profiloDiabetologo.setTitle("Profilo Diabetologo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/profiloDiabetologo.fxml"));
            Parent root = loader.load();
            ModificaDiabetologoController modificaDiabetologoController = loader.getController();
            modificaDiabetologoController.setTaxCode(taxCode);
            profiloDiabetologo.setScene(new Scene(root));
            profiloDiabetologo.show();
        } catch (IOException e){
            System.out.println("Errore caricamento pagina profilo!" + e.getMessage());
        }
        Stage homePageDiabetologo = (Stage) topBar.getScene().getWindow();
        homePageDiabetologo.close();

    }
}
