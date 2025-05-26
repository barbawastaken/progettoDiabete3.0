package controller.Amministratore;

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

        //Qui jack
        loginStage.close(); // chiudi la finestra precedente
        view.start(amministratoreStage, this);

        // Set up evento per il bottone
        view.getAddUserButton().setOnAction(e -> {
            // Crea la vista, il modello e il controller per Aggiungi Utente
            AggiungiUtenteModel aggiungiUtenteModel = new AggiungiUtenteModel();
            AggiungiUtenteView aggiungiUtenteView = new AggiungiUtenteView();
            new AggiungiUtenteController(aggiungiUtenteModel, aggiungiUtenteView, amministratoreStage);
        });

        // Bottone visualizza lista utenti
        view.getViewUserListButton().setOnAction(e -> {
            VisualizzaListaUtentiModel visualizzaListaUtentiModel = new VisualizzaListaUtentiModel();
            VisualizzaListaUtentiView visualizzaListaUtentiView= new VisualizzaListaUtentiView();
            new VisualizzaListaUtentiController(visualizzaListaUtentiModel, visualizzaListaUtentiView, amministratoreStage);
        });
    }
}
