package HomePages;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.LoginModel;
import view.LoginView;

import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            new LoginController(model, view, stage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
