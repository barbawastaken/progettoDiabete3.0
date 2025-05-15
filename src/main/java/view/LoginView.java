package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView {
    private final TextField taxCodeField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button accessButton = new Button("Accedi");
    private final Button resetButton = new Button("Reset");
    private final Group group = new Group();

    public void initialize(Stage stage) {
        HBox hbox = new HBox();
        Scene scene = new Scene(hbox, 320, 240);

        Text taxCodeText = new Text("Username");
        taxCodeText.setX(70);
        taxCodeText.setY(70);
        taxCodeText.setFont(Font.font(16));

        taxCodeField.setLayoutX(70);
        taxCodeField.setLayoutY(80);

        Text passwordText = new Text("Password");
        passwordText.setX(70);
        passwordText.setY(140);
        passwordText.setFont(Font.font(16));

        passwordField.setLayoutX(70);
        passwordField.setLayoutY(150);

        resetButton.setLayoutX(70);
        resetButton.setLayoutY(210);

        accessButton.setLayoutX(160);
        accessButton.setLayoutY(210);

        group.getChildren().addAll(taxCodeText, taxCodeField, passwordText, passwordField, resetButton, accessButton);

        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(group);

        stage.setMinHeight(320);
        stage.setMinWidth(240);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public TextField getTaxCodeField() { return taxCodeField; }

    public PasswordField getPasswordField() { return passwordField; }

    public Button getAccessButton() { return accessButton; }

    public Button getResetButton() { return resetButton; }

    public Group getGroup() { return group; }
}
