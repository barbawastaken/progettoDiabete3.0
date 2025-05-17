package controller.Amministratore;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.VisualizzaListaUtentiView;

import java.util.List;

public class VisualizzaListaUtentiController {
    private VisualizzaListaUtentiModel model ;
    private VisualizzaListaUtentiView view;

    public VisualizzaListaUtentiController(VisualizzaListaUtentiModel model, VisualizzaListaUtentiView view, Stage stage) {
        this.model = model;
        this.view = view;
        view.start(stage, this);

    }

    public void caricaUtenti(TableView<Utente> tabella) {
        ObservableList<Utente> utenti = FXCollections.observableArrayList(model.getTuttiGliUtenti());
        tabella.setItems(utenti);

        //Aggiungo un Context Menu per ogni riga presente nella tabella
        tabella.setRowFactory(utenteTableView -> {
            TableRow<Utente> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            //impostiamo un modifica utente
            MenuItem modificaItem = new MenuItem("Modifica utente");
            modificaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                apriFinestraModifica(selected);
            });
            // impostiamo il delete :)
            MenuItem eliminaItem = new MenuItem("Elimina utente");
            eliminaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                if (selected != null) {
                    model.rimuoviUtente(selected); // Aggiorna anche il DB
                    tabella.getItems().remove(selected); // Rimuovi dalla tabella
                }
            });
            contextMenu.getItems().addAll(modificaItem, eliminaItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });
    }
    private void apriFinestraModifica(Utente utente) {
        System.out.println("Modifica utente: " + utente.getTaxCode());
        // TODO: Qui puoi aprire una nuova finestra o dialog con i dati dell'utente
    }
}