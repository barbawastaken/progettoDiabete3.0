package view.Amministratore;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Amministratore.Utente;
import controller.Amministratore.ModificaUtenteController;

import java.time.LocalDate;
import java.time.ZoneId;

public class ModificaUtenteView {

    private final TextField taxCodeField = new TextField();
    private final TextField passwordField = new TextField();
    private final TextField nomeField = new TextField();
    private final TextField cognomeField = new TextField();
    private final TextField emailField = new TextField();
    private final DatePicker birthDatePicker= new DatePicker();
    private final TextField addressField = new TextField();
    private final TextField numberField = new TextField();
    private final TextField cityField = new TextField();
    private final TextField capField = new TextField();
    private final TextField genderField = new TextField();
    private final TextField telephoneField = new TextField();
    private final TextField userTypeField = new TextField();
    private final TextField diabetologoField = new TextField();

    private final Button salvaButton = new Button("Salva");
    private final Stage stage = new Stage();

    public ModificaUtenteView(Utente utente) {
        stage.setTitle("Modifica Utente: " + utente.getTaxCode());

        taxCodeField.setText(utente.getTaxCode());
        passwordField.setText(utente.getPassword());
        nomeField.setText(utente.getNome());
        cognomeField.setText(utente.getCognome());
        emailField.setText(utente.getEmail());
        birthDatePicker.setValue(LocalDate.parse(utente.getBirthday().toString()));
        addressField.setText(utente.getAddress());
        numberField.setText(String.valueOf(utente.getNumber()));
        cityField.setText(utente.getCity());
        capField.setText(String.valueOf(utente.getCap()));
        genderField.setText(utente.getGender());
        telephoneField.setText(utente.getTelephone());
        userTypeField.setText(utente.getRole());
        diabetologoField.setText(utente.getDiabetologo());


        // salvaButton.setOnAction(e -> controller.salvaModifiche(stage));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15));

        grid.addRow(0, new Label("Codice Fiscale:"), taxCodeField);
        grid.addRow(1, new Label("Password:"), passwordField);
        grid.addRow(2, new Label("Nome:"), nomeField);
        grid.addRow(3, new Label("Cognome:"), cognomeField);
        grid.addRow(4, new Label("Email:"), emailField);
        grid.addRow(5,new Label("Data di nascita:"), birthDatePicker);
        grid.addRow(6, new Label("Indirizzo:"), addressField);
        grid.addRow(7, new Label("Numero civico:"), numberField);
        grid.addRow(8, new Label("Città:"), cityField);
        grid.addRow(9, new Label("CAP:"), capField);
        grid.addRow(10, new Label("Genere:"), genderField);
        grid.addRow(11, new Label("Telefono:"), telephoneField);
        grid.addRow(12, new Label("Tipo utente:"), userTypeField);
        grid.addRow(13, new Label("Diabetologo:"), diabetologoField);
        grid.add(salvaButton, 1, 14);

        stage.setScene(new Scene(grid));
        stage.show();
    }

    public void start(Stage stage, Utente utente, ModificaUtenteController controller) {
        stage.setTitle("Modifica Utente: " + utente.getTaxCode());

        taxCodeField.setText(utente.getTaxCode());
        passwordField.setText(utente.getPassword());
        nomeField.setText(utente.getNome());
        cognomeField.setText(utente.getCognome());
        emailField.setText(utente.getEmail());
        birthDatePicker.setValue(LocalDate.parse(utente.getBirthday().toString()));
        addressField.setText(utente.getAddress());
        numberField.setText(String.valueOf(utente.getNumber()));
        cityField.setText(utente.getCity());
        capField.setText(String.valueOf(utente.getCap()));
        genderField.setText(utente.getGender());
        telephoneField.setText(utente.getTelephone());
        userTypeField.setText(utente.getRole());
        diabetologoField.setText(utente.getDiabetologo());


       // salvaButton.setOnAction(e -> controller.salvaModifiche(stage));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15));

        grid.addRow(0, new Label("Codice Fiscale:"), taxCodeField);
        grid.addRow(1, new Label("Password:"), passwordField);
        grid.addRow(2, new Label("Nome:"), nomeField);
        grid.addRow(3, new Label("Cognome:"), cognomeField);
        grid.addRow(4, new Label("Email:"), emailField);
        grid.addRow(5,new Label("Data di nascita:"), birthDatePicker);
        grid.addRow(6, new Label("Indirizzo:"), addressField);
        grid.addRow(7, new Label("Numero civico:"), numberField);
        grid.addRow(8, new Label("Città:"), cityField);
        grid.addRow(9, new Label("CAP:"), capField);
        grid.addRow(10, new Label("Genere:"), genderField);
        grid.addRow(11, new Label("Telefono:"), telephoneField);
        grid.addRow(12, new Label("Tipo utente:"), userTypeField);
        grid.addRow(13, new Label("Diabetologo:"), diabetologoField);
        grid.add(salvaButton, 1, 14);

        stage.setScene(new Scene(grid));
        stage.show();
    }

    // Getter per tutti i campi
    public String getTaxCode(){ return taxCodeField.getText(); }
    public String getPassword() { return passwordField.getText(); }
    public String getNome() { return nomeField.getText(); }
    public String getCognome() { return cognomeField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getBirthday() { return birthDatePicker.getValue().toString(); }
    public String getAddress() { return addressField.getText(); }
    public int getNumber() { return Integer.parseInt(numberField.getText()); }
    public String getCity() { return cityField.getText(); }
    public int getCap() { return Integer.parseInt(capField.getText()); }
    public String getGender() { return genderField.getText(); }
    public String getTelephone() { return telephoneField.getText(); }
    public String getRole() { return userTypeField.getText(); }
    public String getDiabetologo() { return diabetologoField.getText(); }
    public Button getSalvaButton() { return salvaButton; }
    public Stage getStage() { return stage; }
}