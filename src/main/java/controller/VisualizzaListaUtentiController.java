package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.AggiungiUtenteModel;
import model.Utente;
import model.VisualizzaListaUtentiModel;
import view.VisualizzaListaUtentiView;

public class VisualizzaListaUtentiController {
    private VisualizzaListaUtentiModel model ;
    private VisualizzaListaUtentiView view;

    public VisualizzaListaUtentiController(VisualizzaListaUtentiModel model, VisualizzaListaUtentiView view, Stage stage) {
        this.model = model;
        this.view = view;
        view.start(stage);
    }

    public void caricaUtenti(TableView<Utente> tabella) {
        this.model = model;
        this.view = view;

        ObservableList<Utente> lista = FXCollections.observableArrayList(model.getTuttiGliUtenti());
        tabella.setItems(lista);
    }
}