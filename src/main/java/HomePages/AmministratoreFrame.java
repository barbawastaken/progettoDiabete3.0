package HomePages;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AmministratoreFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {

        Stage amministratoreStage = new Stage();
        amministratoreStage.setScene(new Scene(new HBox(new Group())));
        amministratoreStage.setHeight(loginStage.getHeight());
        amministratoreStage.setWidth(loginStage.getWidth());
        amministratoreStage.setX(loginStage.getX());
        amministratoreStage.setY(loginStage.getY());
        amministratoreStage.alwaysOnTopProperty();
        amministratoreStage.setMinHeight(320);
        amministratoreStage.setMinWidth(240);
        amministratoreStage.setTitle("Homepage amministratore");
        loginStage.close();
        amministratoreStage.show();

    }
}
