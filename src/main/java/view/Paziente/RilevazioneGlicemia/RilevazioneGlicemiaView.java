package view.Paziente.RilevazioneGlicemia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RilevazioneGlicemiaView {

    private final Scene scene;
    private final Button indietroButton = new Button("Indietro");
    private final TextField quantitaRilevazioneTextField = new TextField();
    private final ComboBox<String> momentoGiornataField = new ComboBox<>();
    private final ComboBox<String> prePostField = new ComboBox<>();
    private final DatePicker dataField = new DatePicker();
    private final Button resetButton = new Button("Reset");
    private final Button confermaButton = new Button("Conferma");

    public RilevazioneGlicemiaView() {

        BorderPane root = new BorderPane();

        //Composizione fascia alta

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        headerBox.setMinHeight(100);
        headerBox.setMaxHeight(100);
        headerBox.setPrefHeight(100);

        try {
            Image userImage = new Image(getClass().getResource("/images/userImage.png").toExternalForm());

            ImageView userImageView = new ImageView(userImage);
            userImageView.setFitWidth(100);
            userImageView.setFitHeight(100);
            headerBox.getChildren().add(userImageView);

        } catch (Exception e) {
            System.out.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }

        Region spacer = new Region(); // Spazio flessibile tra immagine e bottone
        HBox.setHgrow(spacer, Priority.ALWAYS);

        indietroButton.setPadding(new Insets(10, 10, 10, 10));

        headerBox.getChildren().add(spacer);
        headerBox.getChildren().add(indietroButton);

        // Campi centrali per l'inserimento dei valori della rilevazione

        Text quantitaRilevazioneText = new Text("Quantità rilevata");
        quantitaRilevazioneText.setFont(new Font(16));

        quantitaRilevazioneTextField.setPromptText("in mg");

        Text momentoGiornataText = new Text("Momento della giornata");
        momentoGiornataText.setFont(new Font(16));

        momentoGiornataField.getItems().addAll("Colazione", "Pranzo", "Cena");

        Text prePostText = new Text("Pre/Post");
        prePostText.setFont(new Font(16));

        prePostField.getItems().addAll("Pre", "Post");

        Text dataText = new Text("Data");
        dataText.setFont(new Font(16));

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(resetButton, confermaButton);

        VBox centralBox = new VBox();
        centralBox.getChildren().addAll(
                quantitaRilevazioneText, quantitaRilevazioneTextField,
                momentoGiornataText, momentoGiornataField,
                prePostText, prePostField,
                dataText, dataField,
                buttonBox
        );

        root.setTop(headerBox);
        root.setCenter(centralBox);
        this.scene = new Scene(root);
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());
    }

    public Scene getScene() {
        return scene;
    }

    public Button getIndietroButton() {
        return indietroButton;
    }

    public TextField getQuantitaRilevazioneTextField() {
        return quantitaRilevazioneTextField;
    }

    public ComboBox<String> getMomentoGiornataField() {
        return momentoGiornataField;
    }

    public ComboBox<String> getPrePostField() {
        return prePostField;
    }

    public DatePicker getDataField() {
        return dataField;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getConfermaButton() {
        return confermaButton;
    }
}
