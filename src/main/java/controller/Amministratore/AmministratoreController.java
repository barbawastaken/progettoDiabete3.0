package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Amministratore.AggiungiUtenteModel;
import model.Amministratore.AmministratoreModel;
import view.Amministratore.AggiungiUtenteView;
import view.Amministratore.AmministratoreView;

import java.io.IOException;


public class AmministratoreController {

    @FXML
    private void isInserisciUtenteClicked() throws IOException {
        AggiungiUtenteController controller = new AggiungiUtenteController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/aggiungi_utente_view.fxml"));
        Parent root = loader.load();

        controller = loader.getController();


        Stage stage = new Stage();
        stage.setTitle("Aggiungi Utente");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void isVisualizzaUtentiClicked() throws IOException {
        VisualizzaListaUtentiController controller = new VisualizzaListaUtentiController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/visualizza_utenti_view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Visualizza Utente");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public AmministratoreController(){

    }

    public AmministratoreController(AmministratoreModel model, AmministratoreView view, Stage loginStage) {


        Stage amministratoreStage = new Stage();



        amministratoreStage.setScene(view.getScene());
        amministratoreStage.setHeight(loginStage.getHeight());
        amministratoreStage.setWidth(loginStage.getWidth());
        amministratoreStage.setX(loginStage.getX());
        amministratoreStage.setY(loginStage.getY());
        amministratoreStage.alwaysOnTopProperty();
        amministratoreStage.setMinHeight(320);
        amministratoreStage.setMinWidth(240);
        amministratoreStage.setTitle("Homepage amministratore");

        loginStage.close();
        amministratoreStage.show();
    }
}
