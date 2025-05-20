package HomePages;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.LoginModel;
import view.LoginView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlView/login_view.fxml")));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 650, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}