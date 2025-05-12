package HomePages;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PazienteFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {

        Stage pazienteStage = new Stage();
        pazienteStage.setScene(new Scene(new HBox(new Group())));

        pazienteStage.setHeight(loginStage.getHeight());
        pazienteStage.setWidth(loginStage.getWidth());
        pazienteStage.setX(loginStage.getX());
        pazienteStage.setY(loginStage.getY());
        pazienteStage.alwaysOnTopProperty();
        pazienteStage.setMinHeight(320);
        pazienteStage.setMinWidth(240);

        pazienteStage.setTitle("Homepage paziente");
        loginStage.close();
        pazienteStage.show();


    }
}
