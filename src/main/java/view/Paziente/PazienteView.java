package view.Paziente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class PazienteView {

    private final Scene scene;
    private final Button logoutButton = new Button("Logout");
    private final Button rilevazioniGlicemiaButton = new Button("Rilevazioni Glicemia");
    private final Button inserimentoSintomiButton = new Button("Inserimento Sintomi");
    private final Button assunzioneFarmaciButton = new Button("Assunzione Farmaci");
    private final Button disturbiTerapieButton = new Button("Disturbi/Terapie concomitanti");

    public PazienteView() {
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

        logoutButton.setPadding(new Insets(10, 10, 10, 10));

        headerBox.getChildren().add(spacer);
        headerBox.getChildren().add(logoutButton);

        //Composizione fascia centrale

        Group firstRow = new Group();
        Group secondRow = new Group();

        rilevazioniGlicemiaButton.setLayoutX(30);
        rilevazioniGlicemiaButton.setLayoutY(100);
        //rilevazioniGlicemiaButton.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        rilevazioniGlicemiaButton.setFont(new Font(18));

        inserimentoSintomiButton.setLayoutX(230);
        inserimentoSintomiButton.setLayoutY(100);
        //inserimentoSintomiButton.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        inserimentoSintomiButton.setFont(new Font(18));


        assunzioneFarmaciButton.setLayoutX(30);
        assunzioneFarmaciButton.setLayoutY(250);
        //assunzioneFarmaciButton.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        assunzioneFarmaciButton.setFont(new Font(18));

        disturbiTerapieButton.setLayoutX(230);
        disturbiTerapieButton.setLayoutY(250);
        //disturbiTerapieButton.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        disturbiTerapieButton.setFont(new Font(18));

        firstRow.getChildren().add(rilevazioniGlicemiaButton);
        firstRow.getChildren().add(inserimentoSintomiButton);

        secondRow.getChildren().add(assunzioneFarmaciButton);
        secondRow.getChildren().add(disturbiTerapieButton);

        Group centralBox = new Group();
        centralBox.getChildren().addAll(firstRow, secondRow);

        root.setTop(headerBox);
        root.setCenter(centralBox);

        this.scene = new Scene(root);
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

    }

    public Scene getScene() {
        return scene;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Button getRilevazioniGlicemiaButton() {
        return rilevazioniGlicemiaButton;
    }

    public Button getInserimentoSintomiButton() {
        return inserimentoSintomiButton;
    }

    public Button getAssunzioneFarmaciButton() {
        return assunzioneFarmaciButton;
    }

    public Button getDisturbiTerapieButton() {
        return disturbiTerapieButton;
    }
}

