package HomePages;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AggiungiUtenteFrame extends Application {

    public AggiungiUtenteFrame(){

    }
    @Override
    public void start(Stage stage) throws Exception {

        Stage addUser = new Stage();
        addUser.setTitle("Aggiungi Utente");

        HBox hbox = new HBox();
        Scene scene = new Scene(hbox, 320, 240);


        stage.setScene(scene);
        stage.setTitle("Aggiungi Utente");
        stage.show();

    }
}
