package controller.Amministratore;

import javafx.stage.Stage;
import model.Amministratore.AggiungiUtenteModel;
import model.Amministratore.AmministratoreModel;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.AggiungiUtenteView;
import view.Amministratore.AmministratoreView;
import view.Amministratore.VisualizzaListaUtentiView;

public class AmministratoreController {

    private final AmministratoreModel model;
    private final AmministratoreView view;

    public AmministratoreController(AmministratoreModel model, AmministratoreView view, Stage loginStage) {
        this.model = model;
        this.view = view;

        Stage amministratoreStage = new Stage();

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
