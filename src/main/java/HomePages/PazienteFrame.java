package HomePages;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PazienteFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {

        Stage pazienteStage = new Stage();
        pazienteStage.setTitle("Homepage paziente");

        pazienteStage.setHeight(loginStage.getHeight());
        pazienteStage.setWidth(loginStage.getWidth());
        pazienteStage.setX(loginStage.getX());
        pazienteStage.setY(loginStage.getY());
        pazienteStage.alwaysOnTopProperty();
        pazienteStage.setMinHeight(320);
        pazienteStage.setMinWidth(240);

        Rectangle upperShape = new Rectangle(1980, 100);
        upperShape.setFill(Color.DARKBLUE);

        //HBox upperShapeBox = new HBox(upperShape);

        Image userImage = new Image(getClass().getResource("/images/userImage.png").toExternalForm());

        ImageView userImageView = new ImageView(userImage);
        userImageView.setFitHeight(100);
        userImageView.setFitWidth(100);

        StackPane upperShapePane = new StackPane();
        upperShapePane.getChildren().add(upperShape);
        upperShapePane.getChildren().add(userImageView);
        Scene upperShapeScene = new Scene(upperShapePane);

        upperShapePane.alignmentProperty().set(Pos.TOP_LEFT);
        pazienteStage.setScene(upperShapeScene);


        loginStage.close();
        pazienteStage.show();


    }
}
