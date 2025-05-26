package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class LoginView {
    public final TextField taxCodeField = new TextField();                      //probabilmente da modificare la visibilità
    private final PasswordField passwordField = new PasswordField();
    private final Button accessButton = new Button("Accedi");
    private final Button resetButton = new Button("Reset");
    private final Group group = new Group();

    public void initialize(Stage stage) throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlView/LoginView.fxml")));
       stage.setScene(new Scene(root, 650, 500));
       stage.setTitle("Login");
       stage.show();

    }


    public TextField getTaxCodeField() { return taxCodeField; }

    public PasswordField getPasswordField() { return passwordField; }

    public Button getAccessButton() { return accessButton; }

    public Button getResetButton() { return resetButton; }

    public Group getGroup() { return group; }
}
