package HomePages;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DiabetologoFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {

        Stage diabetologoStage = new Stage();
        diabetologoStage.setScene(new Scene(new HBox(new Group())));
        diabetologoStage.setHeight(loginStage.getHeight());
        diabetologoStage.setWidth(loginStage.getWidth());
        diabetologoStage.setX(loginStage.getX());
        diabetologoStage.setY(loginStage.getY());
        diabetologoStage.alwaysOnTopProperty();
        diabetologoStage.setMinHeight(320);
        diabetologoStage.setMinWidth(240);
        diabetologoStage.setTitle("Homepage diabetologo");
        loginStage.close();
        diabetologoStage.show();

    }
}
