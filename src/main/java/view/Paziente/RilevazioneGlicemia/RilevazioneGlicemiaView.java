package view.Paziente.RilevazioneGlicemia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class RilevazioneGlicemiaView {

    private Scene scene;
    private final Button indietroButton = new Button("Indietro");

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

        root.setTop(headerBox);
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
}
