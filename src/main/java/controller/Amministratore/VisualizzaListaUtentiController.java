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
import view.Amministratore.ModificaUtenteView;
import view.Amministratore.VisualizzaListaUtentiView;

import java.util.List;

public class VisualizzaListaUtentiController {
    private VisualizzaListaUtentiModel model ;
    private VisualizzaListaUtentiView view;
    private Stage listaUtentiStage;

    public VisualizzaListaUtentiController(VisualizzaListaUtentiModel model, VisualizzaListaUtentiView view, Stage stage) {
        this.model = model;
        this.view = view;
        this.listaUtentiStage = stage;
        view.start(stage, this); // chiama view.start e caricaUtenti

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
        ModificaUtenteView modificaView = new ModificaUtenteView();
        ModificaUtenteController modificaController = new ModificaUtenteController(modificaView, utente, model, listaUtentiStage);
        Stage modificaStage = new Stage();
        modificaView.start(modificaStage, utente, modificaController);
        // Quando la finestra viene chiusa, ricarica la tabella
        //modificaStage.setOnHiding(e -> caricaUtenti(view.getTabellaUtenti())); // <-- ricarica la tabella
    }

    public void start(Stage stage) {
        view.start(stage, this); // chiama la view, passandole anche il controller
    }
}