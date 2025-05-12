package HomePages;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class PazienteFrame extends Application {

    private String nomePaziente;

    public PazienteFrame(String nomePaziente) {
        this.nomePaziente = nomePaziente;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {

        Stage pazienteStage = new Stage();

        Group pazienteGroup = new Group();
        Label welcome = new Label("Benvenuto " + nomePaziente + "!!");
        welcome.setLayoutX(100);
        welcome.setLayoutY(50);
        pazienteGroup.getChildren().add(welcome);

        pazienteStage.setScene(new Scene(new HBox(pazienteGroup)));

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
