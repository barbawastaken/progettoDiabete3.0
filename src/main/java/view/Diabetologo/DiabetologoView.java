package view.Diabetologo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DiabetologoView {

    private StackPane layout;

    public DiabetologoView() {
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

        layout.getChildren().add(header);
        if (userImageView != null)
            layout.getChildren().add(userImageView);

        layout.setAlignment(Pos.TOP_LEFT);
    }

    public Scene getScene() {
        return new Scene(layout);
    }
}
