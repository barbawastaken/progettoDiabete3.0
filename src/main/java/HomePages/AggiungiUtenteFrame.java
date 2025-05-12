package HomePages;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.DriverManager;

import java.sql.*;

public class AggiungiUtenteFrame extends Application {

    public AggiungiUtenteFrame(){

    }
    @Override
    public void start(Stage stage) throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlite:mydatabase.db";
        Stage addUser = new Stage();
        addUser.setTitle("Aggiungi Utente");

        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 320, 240);
        Group group = new Group();


        Text usernameText = new Text("Username");
        usernameText.setX(70);
        usernameText.setY(70);
        usernameText.setFont(Font.font(16));
        group.getChildren().add(usernameText);

        TextField usernameField = new TextField();
        usernameField.setLayoutX(70);
        usernameField.setLayoutY(80);
        group.getChildren().add(usernameField);

        Text passwordText = new Text("Password");
        passwordText.setX(70);
        passwordText.setY(140);
        passwordText.setFont(Font.font(16));
        group.getChildren().add(passwordText);

        TextField passwordField = new TextField();
        passwordField.setLayoutX(70);
        passwordField.setLayoutY(150);
        group.getChildren().add(passwordField);

        Button resetButton = new Button("Reset");
        resetButton.setLayoutX(70);
        resetButton.setLayoutY(210);
        resetButton.setOnAction(buttonPressed -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        Button sendButton = new Button("Aggiungi Utente");
        sendButton.setLayoutX(90);
        sendButton.setLayoutY(210);

        sendButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent buttonPressed) {

                try (Connection conn = DriverManager.getConnection(url)) {
                    String query = "INSERT INTO loginTable (username, password, userType) VALUES (?, ?, ?);";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, usernameField.getText());
                    pstmt.setString(2, passwordField.getText());
                    pstmt.setString(3, "admin");
                    pstmt.executeUpdate();
                    System.out.println("Dati inseriti");



                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        vbox.getChildren().add(group);
        vbox.getChildren().add(resetButton);
        vbox.getChildren().add(sendButton);
        stage.setScene(scene);
        stage.setTitle("Aggiungi Utente");
        stage.show();

    }
}
