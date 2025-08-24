package HomePages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainDue extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the main application view

        URL mainViewUrl = Objects.requireNonNull(getClass().getResource("/fxmlView/main_view.fxml"));
        FXMLLoader loader = new FXMLLoader(mainViewUrl);

        Parent root = loader.load();
        System.out.println("loader.load eseguito");
        // Set up the scene
        Scene scene = new Scene(root, 800, 600);

        /*
        css da implementare quando funzionerà tutto
         */
        //URL cssUrl = getClass().getResource("/resources/css/styles.css");
        //scene.getStylesheets().add(cssUrl.toExternalForm());

        // Configure and show the stage
        primaryStage.setTitle("Dash App - JavaFX Version");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
