package HomePages;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AmministratoreFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) {
        Group group = new Group();
        Stage amministratoreStage = new Stage();



        Button addUser = new Button("Aggiungi un nuovo utente");            //Creo il bottone che aprirà AggiungiUtenteFrame e che mi farà inserire un utente
        addUser.setOnAction(e -> {
            try {
                AggiungiUtenteFrame aggiungiUtenteFrame = new AggiungiUtenteFrame();    //All'azione di premere il pulsante, viene creato il frame e viene eseguito
                                                                                        //il metodo 'start' in modo da far apparire la pagina

                aggiungiUtenteFrame.start(amministratoreStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        addUser.setLayoutX(70);
        addUser.setLayoutY(210);

        group.getChildren().add(addUser); //Aggiungo il bottone a "group"

        Scene scene = new Scene(group);      //Metto nella scena "scene" vBox

        amministratoreStage.setScene(scene);    //Metto nello stage "scene"
        amministratoreStage.setHeight(loginStage.getHeight());
        amministratoreStage.setWidth(loginStage.getWidth());
        amministratoreStage.setX(loginStage.getX());
        amministratoreStage.setY(loginStage.getY());
        amministratoreStage.alwaysOnTopProperty();
        amministratoreStage.setMinHeight(320);          //Grandezza uguale agli altri elementi
        amministratoreStage.setMinWidth(240);
        amministratoreStage.setTitle("Homepage amministratore");
        loginStage.close();
        amministratoreStage.show();

    }
}
