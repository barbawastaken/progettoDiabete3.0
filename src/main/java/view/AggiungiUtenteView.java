package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AggiungiUtenteView {

    public TextField usernameField = new TextField();
    public TextField passwordField = new TextField();
    public DatePicker datePicker = new DatePicker();
    public Button resetButton = new Button("Reset");
    public Button sendButton = new Button("Aggiungi Utente");
    public ToggleGroup toggleGroup = new ToggleGroup();

    public RadioButton admin = new RadioButton("AMMINISTRATORE");
    public RadioButton diab = new RadioButton("DIABETOLOGO");
    public RadioButton utente = new RadioButton("PAZIENTE");

    private VBox layout = new VBox();

    public void start(Stage stage) {
        Text usernameText = new Text("Username");
        usernameText.setFont(Font.font(16));

        Text dateText = new Text("Data di Nascita");
        dateText.setFont(Font.font(16));

        Text passwordText = new Text("Password");
        passwordText.setFont(Font.font(16));

        admin.setToggleGroup(toggleGroup);
        diab.setToggleGroup(toggleGroup);
        utente.setToggleGroup(toggleGroup);

        layout.getChildren().addAll(
                usernameText, usernameField,
                dateText, datePicker,
                passwordText, passwordField,
                admin, diab, utente,
                resetButton, sendButton
        );

        Scene scene = new Scene(layout, 350, 500);
        stage.setScene(scene);
        stage.setTitle("Aggiungi Utente");
        stage.show();
    }
}
