package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.AggiungiUtenteModel;
import view.AggiungiUtenteView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

public class AggiungiUtenteController {

    private AggiungiUtenteModel model;
    private AggiungiUtenteView view;

    public AggiungiUtenteController(AggiungiUtenteModel model, AggiungiUtenteView view, Stage stage) {
        this.model = model;
        this.view = view;
        view.start(stage);
        initController();
    }

    private void initController() {
        view.resetButton.setOnAction(e -> {
            view.usernameField.setText("");
            view.passwordField.setText("");
        });

        view.sendButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Toggle selectedToggle = view.toggleGroup.getSelectedToggle();
                    if (selectedToggle != null) {
                        RadioButton selected = (RadioButton) selectedToggle;
                        String username = view.usernameField.getText();
                        String password = view.passwordField.getText();
                        String userType = selected.getText();

                        model.inserisciUtente(username, password, userType);
                        System.out.println("Dati inseriti");
                    } else {
                        System.out.println("Nessun tipo utente selezionato");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
