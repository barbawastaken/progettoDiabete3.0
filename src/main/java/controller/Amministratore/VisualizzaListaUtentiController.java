package controller.Amministratore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.VisualizzaListaUtentiView;

public class VisualizzaListaUtentiController {
    private VisualizzaListaUtentiModel model ;
    private VisualizzaListaUtentiView view;

    public VisualizzaListaUtentiController(VisualizzaListaUtentiModel model, VisualizzaListaUtentiView view, Stage stage) {
        this.model = model;
        this.view = view;
        view.start(stage, this);

    }

    public void caricaUtenti(TableView<Utente> tabella) {
        /*this.model = model;
        this.view = view;*/

        ObservableList<Utente> lista = FXCollections.observableArrayList(model.getTuttiGliUtenti());
        tabella.setItems(lista);
    }
}