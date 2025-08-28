package controller.Diabetologo;

import controller.*;
import controller.Amministratore.VisualizzaListaUtentiController;
import controller.Paziente.ModificaPazienteController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import model.Diabetologo.DiabetologoModel;
import model.NotificationModel;
import model.getUsefulInfos;
import view.Diabetologo.DiabetologoView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiabetologoController {


    @FXML private HBox navbarContainer;

    public DiabetologoController() { }


    @FXML
    public void isVisualizzaPazientiClicked() throws IOException {

        ViewNavigator.navigateToVisualizzaPazienti();

    }

    @FXML
    public void isVisualizzaNotificheClicked() {

    }

    @FXML
    public void initialize() throws SQLException {
        Session.getInfos();
        NotificationModel notificationModel = new NotificationModel(Session.getInstance().getTaxCode());
        ArrayList<String> results = notificationModel.notifyRitardo();
        System.out.println(results.toString());
        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

    }

    public DiabetologoController(DiabetologoModel diabetologoModel, DiabetologoView diabetologoView, Stage loginStage){



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

    /*

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

*/

    @FXML
    private void onProfiloClicked(){
        try{
            Stage profiloDiabetologo = new Stage();
            profiloDiabetologo.setTitle("Profilo Diabetologo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/profiloDiabetologo.fxml"));
            Parent root = loader.load();
            profiloDiabetologo.setScene(new Scene(root));
            profiloDiabetologo.show();
        } catch (IOException e){
            System.out.println("Errore caricamento pagina profilo!" + e.getMessage());
        }

    }
}
