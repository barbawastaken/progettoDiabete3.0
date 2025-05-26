package view.Paziente.AggiuntaSintomi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AggiuntaSintomiView {

    private final Scene scene;
    private final Button indietroButton = new Button("Indietro");
    private final ComboBox<String> sintomiPrincipaliComboBox = new ComboBox<>();
    private final TextArea specificaAltroField = new TextArea();
    private final DatePicker dataRilevazioneSintomoPicker = new DatePicker();
    private final Button resetButton = new Button("Reset");
    private final Button confermaButton = new Button("Conferma");

    public AggiuntaSintomiView() {

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

        //Composizione campi centrali

        Text sintomiPrincipaliText = new Text("Sintomi principali");
        sintomiPrincipaliText.setFont(new Font(16));

        sintomiPrincipaliComboBox.getItems().addAll("", "Spossatezza", "Nausea", "Mal di testa");

        Text specificatoAltroText = new Text("Specificato altro");
        specificatoAltroText.setFont(new Font(16));

        specificaAltroField.setPromptText("Specifica altri sintomi");

        Text dataRilevazioneSintomoText = new Text("Data di rilevazione del sintomo");
        dataRilevazioneSintomoText.setFont(new Font(16));

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(resetButton, confermaButton);

        VBox centralBox = new VBox();
        centralBox.getChildren().addAll(
                sintomiPrincipaliText, sintomiPrincipaliComboBox,
                specificatoAltroText, specificaAltroField,
                dataRilevazioneSintomoText, dataRilevazioneSintomoPicker,
                buttonBox
        );

        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(centralBox);
        scene = new Scene(root);
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());
    }

    public Scene getScene() { return scene; }
    public Button getIndietroButton() { return indietroButton; }
    public ComboBox<String> getSintomiPrincipaliComboBox() { return sintomiPrincipaliComboBox; }
    public TextArea getSpecificaAltroField() { return specificaAltroField; }
    public DatePicker getDataRilevazioneSintomoPicker() { return dataRilevazioneSintomoPicker; }
    public Button getResetButton() { return resetButton; }
    public Button getConfermaButton() { return confermaButton; }
}
