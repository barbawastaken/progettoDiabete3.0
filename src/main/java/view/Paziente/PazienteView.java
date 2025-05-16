package view.Paziente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

import java.awt.*;

public class PazienteView {

    private StackPane root;
    private Button logoutButton = new Button("Logout");

    public PazienteView() {
        root = new StackPane();

        // Fascia blu piena larghezza
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        headerBox.setMinHeight(100);
        headerBox.setMaxHeight(100);
        headerBox.setPrefHeight(100);

        ImageView userImageView = null;
        try {
            Image userImage = new Image(getClass().getResource("/images/userImage.png").toExternalForm());
            userImageView = new ImageView(userImage);
            userImageView.setFitWidth(100);
            userImageView.setFitHeight(100);
        } catch (Exception e) {
            System.out.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }

        Region spacer = new Region(); // Spazio flessibile tra immagine e bottone
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerBox.getChildren().addAll(userImageView, spacer, logoutButton);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().add(headerBox);
        VBox.setVgrow(headerBox, Priority.NEVER);

        // Fai occupare tutto lo spazio
        root.getChildren().add(mainLayout);
        root.setPrefSize(800, 600); // inizialmente, ma si adatta

        // Stretch automatico
        mainLayout.prefWidthProperty().bind(root.widthProperty());
        headerBox.prefWidthProperty().bind(mainLayout.widthProperty());
    }

    public Scene getScene() {
        return new Scene(root, 800, 600);
    }

    /*private HBox hbox = new HBox();
    private StackPane layout;
    Button logoutButton = new Button("Logout");

    public PazienteView() {
        layout = new StackPane();

        Rectangle header = new Rectangle(1980, 100);
        header.setFill(Color.DARKBLUE);

        ImageView userImageView = null;
        try {
            Image userImage = new Image(getClass().getResource("/images/userImage.png").toExternalForm());
            userImageView = new ImageView(userImage);
            userImageView.setFitWidth(100);
            userImageView.setFitHeight(100);
        } catch (Exception e) {
            System.out.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }

        logoutButton.setLayoutX(250);
        logoutButton.setLayoutY(250);

        layout.getChildren().add(header);
        if (userImageView != null)
            layout.getChildren().add(userImageView);

        layout.setAlignment(Pos.TOP_LEFT);
        hbox.getChildren().add(layout);
        hbox.getChildren().add(logoutButton);
    }

    public Scene getScene() {
        return new Scene(hbox);
    }*/
}

